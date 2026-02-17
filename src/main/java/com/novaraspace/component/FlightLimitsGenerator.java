package com.novaraspace.component;

import com.novaraspace.model.domain.PaddingRangeParams;
import com.novaraspace.model.domain.PaddingRangeResult;
import com.novaraspace.model.dto.flight.FlightLimitsDTO;
import com.novaraspace.model.dto.flight.FlightSearchQueryDTO;
import com.novaraspace.model.entity.Location;
import com.novaraspace.model.exception.FailedOperationException;
import com.novaraspace.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class FlightLimitsGenerator {

    private final LocationRepository locationRepository;
    @Value("${app.flight.padding-days-on-fetch}")
    private int paddingRange;

    public FlightLimitsGenerator(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public FlightLimitsDTO generateLimits(FlightSearchQueryDTO queryDTO) {
        if (queryDTO == null || queryDTO.getDepartureDate() == null) {
            throw new FailedOperationException();
        }
        if (!queryDTO.hasReturnFlight()) {
            return getLimitsForOneWayFlightSearch(queryDTO.getDepartureDate());
        }

        double departureSeparationFactor = locationRepository.findByCode(queryDTO.getDepartureCode())
                .orElseThrow(FailedOperationException::new)
                .getRegion().getSeparationFactor();
        double arrivalSeparationFactor = locationRepository.findByCode(queryDTO.getArrivalCode())
                .orElseThrow(FailedOperationException::new)
                .getRegion().getSeparationFactor();
        double netSeparationFactor = Math.abs(departureSeparationFactor - arrivalSeparationFactor);
        int separationDays = (int) Math.round(netSeparationFactor);

        PaddingRangeParams departureFlParams = new PaddingRangeParams(
                queryDTO.getDepartureDate(),
                queryDTO.getReturnDate(),
                separationDays,
                null);
        PaddingRangeResult departurePaddingRange = getPaddingRangeForDepartureFlight(departureFlParams);

        PaddingRangeParams returnFlParams = new PaddingRangeParams(
                queryDTO.getDepartureDate(),
                queryDTO.getReturnDate(),
                separationDays,
                departurePaddingRange.latestDate());
        PaddingRangeResult returnPaddingRange = getPaddingRangeForReturnFlight(returnFlParams);

        return new FlightLimitsDTO()
                .setDepartureLowerDate(departurePaddingRange.earliestDate())
                .setDepartureUpperDate(departurePaddingRange.latestDate())
                .setReturnLowerDate(returnPaddingRange.earliestDate())
                .setReturnUpperDate(returnPaddingRange.latestDate());
    }

    private FlightLimitsDTO getLimitsForOneWayFlightSearch(LocalDate selectedDepartureDate) {
        return new FlightLimitsDTO()
                .setDepartureLowerDate(getTrueEarliestDepartureDate(selectedDepartureDate))
                .setDepartureUpperDate(selectedDepartureDate.plusDays(paddingRange))
                .setReturnLowerDate(LocalDate.now())
                .setReturnUpperDate(LocalDate.now());
    }

    private PaddingRangeResult getPaddingRangeForDepartureFlight(PaddingRangeParams params) {
        LocalDate trueEarliestDeparture = getTrueEarliestDepartureDate(params.departureDate());

        LocalDate latestPossibleDeparture = params.returnDate().minusDays(params.separationDays());
        LocalDate trueLatestDeparture = getBalancedLatestDeparture(params.departureDate(), latestPossibleDeparture);
        return new PaddingRangeResult(trueEarliestDeparture, trueLatestDeparture);
    }

    private PaddingRangeResult getPaddingRangeForReturnFlight(PaddingRangeParams params) {
        LocalDate departureTrueLatest = params.departureUpperLimitDate();

        LocalDate earliestPossibleReturn = departureTrueLatest.plusDays(params.separationDays());
        LocalDate earliestReturnWithPaddingRange = params.returnDate().minusDays(paddingRange);

        LocalDate trueEarliestReturn = earliestPossibleReturn.isAfter(earliestReturnWithPaddingRange)
                ? earliestPossibleReturn
                : earliestReturnWithPaddingRange;

        LocalDate trueLatestReturn = params.returnDate().plusDays(paddingRange);
        return new PaddingRangeResult(trueEarliestReturn, trueLatestReturn);
    }

    private LocalDate getTrueEarliestDepartureDate(LocalDate selectedDepartureDate) {
        LocalDate earliestPossibleDeparture = LocalDate.now().plusDays(1);
        LocalDate earliestDepartureWithPaddingRange = selectedDepartureDate.minusDays(paddingRange);
        return earliestPossibleDeparture.isBefore(earliestDepartureWithPaddingRange)
                ? earliestDepartureWithPaddingRange
                : earliestPossibleDeparture;
    }

    // Ensure padding range is divided between the departure and return flight, instead of having
    // 14 days in the departure range and 1 day in the return range if the flights are separated
    // by 15 days for example
    private LocalDate getBalancedLatestDeparture(LocalDate selectedDepartureDate, LocalDate latestPossibleDeparture) {
        long daysBetween = ChronoUnit.DAYS.between(selectedDepartureDate, latestPossibleDeparture);

        if (daysBetween >= paddingRange * 2L) {
            return selectedDepartureDate.plusDays(paddingRange);
        } else if (daysBetween <= 1) {
            return selectedDepartureDate.plusDays(daysBetween);
        }

        long balancedUpperRangeDays = (daysBetween / 2) + (daysBetween % 2);
        return selectedDepartureDate.plusDays(balancedUpperRangeDays);
    }




}
