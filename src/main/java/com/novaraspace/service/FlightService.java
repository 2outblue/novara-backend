package com.novaraspace.service;

import com.novaraspace.model.dto.flight.FlightLimitsDTO;
import com.novaraspace.model.dto.flight.FlightSearchQueryDTO;
import com.novaraspace.model.dto.flight.FlightSearchResultDTO;
import com.novaraspace.model.dto.flight.FlightUiDTO;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.entity.FlightTemplate;
import com.novaraspace.model.entity.Location;
import com.novaraspace.model.exception.FlightException;
import com.novaraspace.model.mapper.FlightMapper;
import com.novaraspace.repository.FlightInstanceRepository;
import com.novaraspace.repository.FlightTemplateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final int availabilityMonthsFromToday = 6;

    @Value("${app.flight.padding-days-on-fetch}")
    private int paddingRange;

    private final FlightTemplateRepository flightTemplateRepository;
    private final FlightInstanceRepository flightInstanceRepository;
    private final LocationService locationService;
    private final FlightMapper flightMapper;

    public FlightService(FlightTemplateRepository flightTemplateRepository, FlightInstanceRepository flightInstanceRepository, LocationService locationService, FlightMapper flightMapper) {
        this.flightTemplateRepository = flightTemplateRepository;
        this.flightInstanceRepository = flightInstanceRepository;
        this.locationService = locationService;
        this.flightMapper = flightMapper;
    }


    public List<LocalDate> getRouteAvailability(String departureCode, String arrivalCode) {
        long dptId = locationService.getLocationByCode(departureCode).getId();
        long arrId = locationService.getLocationByCode(arrivalCode).getId();

        List<Long> matchingTemplates = flightTemplateRepository.findAllByDepartureAndArrivalLocationIds(dptId, arrId)
                .stream().map(FlightTemplate::getId)
                .toList();

        if (matchingTemplates.isEmpty()) {throw FlightException.noAvailability();}

        LocalDate availabilityStartDate = LocalDate.now().plusDays(1);
        LocalDate endDate = availabilityStartDate.plusMonths(availabilityMonthsFromToday);

        return flightInstanceRepository.findDistinctAvailabilityDates(availabilityStartDate, endDate, matchingTemplates);
    }


    public FlightSearchResultDTO getFlightSearchResult(FlightSearchQueryDTO queryDTO) {
        Location departureLocation = locationService.getLocationByCode(queryDTO.getDepartureCode());
        Location arrivalLocation = locationService.getLocationByCode(queryDTO.getArrivalCode());

        List<Long> departureTemplateIds = flightTemplateRepository
                .findAllByDepartureAndArrivalLocationIds(departureLocation.getId(), arrivalLocation.getId())
                .stream().map(fi -> fi.getId()).toList();

        List<Long> returnTemplateIds = flightTemplateRepository
                .findAllByDepartureAndArrivalLocationIds(arrivalLocation.getId(), departureLocation.getId())
                .stream().map(fi -> fi.getId()).toList();
        if (departureTemplateIds.isEmpty() || returnTemplateIds.isEmpty()) {throw FlightException.noAvailability();}

        double netSeparationFactor = Math.abs(departureLocation.getRegion().getSeparationFactor() - arrivalLocation.getRegion().getSeparationFactor());
        LocalDate[] departureFlightPaddingRange = getPaddingRangeForDepartureFlight(queryDTO, netSeparationFactor);
        LocalDate departureUpperLimitDate = departureFlightPaddingRange[1];
        LocalDate[] returnFlightPaddingRange = getPaddingRangeForReturnFlight(queryDTO, netSeparationFactor, departureUpperLimitDate);

        List<FlightUiDTO> departureFlights = getValidInstancesWithinRange(departureTemplateIds, departureFlightPaddingRange, queryDTO.getTotalPaxCount())
                .stream().map(flightMapper::instanceToFlightUiDTO)
                .toList();

        List<FlightUiDTO> returnFlights = getValidInstancesWithinRange(returnTemplateIds, returnFlightPaddingRange, queryDTO.getTotalPaxCount())
                .stream().map(flightMapper::instanceToFlightUiDTO)
                .toList();

        FlightLimitsDTO limits = new FlightLimitsDTO()
                .setDepartureLowerDate(departureFlightPaddingRange[0])
                .setDepartureUpperDate(departureFlightPaddingRange[1])
                .setArrivalLowerDate(returnFlightPaddingRange[0])
                .setArrivalUpperDate(returnFlightPaddingRange[1]);

        if (departureFlights.isEmpty() || returnFlights.isEmpty()) {throw FlightException.noAvailability();}
        return new FlightSearchResultDTO()
                .setDepartureFlights(departureFlights)
                .setReturnFlights(returnFlights)
                .setLimits(limits);
    }

    private List<FlightInstance> getValidInstancesWithinRange(List<Long> templateIds, LocalDate[] paddingRange, int paxCount) {
        return flightInstanceRepository
                .findAllWithTemplateIdsAndWithinRange(templateIds, paddingRange[0], paddingRange[1])
                .stream().filter(fi -> {
                    return fi.getFirstClass().getAvailableSeats() >= paxCount
                            || fi.getMiddleClass().getAvailableSeats() >= paxCount
                            || fi.getLowerClass().getAvailableSeats() >= paxCount;
                }).toList();
    }

    //TODO: You need to refactor the padding range methods, also make the separation between the flights
    // more equal (if there are 6 possible days of separation between departure and return, you can split
    // 3/3 for each instead of 6 for departure and 0 for return) - maybe if the actual separation is at least
    // 2x the minimum, you can just split in half ? (And if the sep days are an odd number assign the bigger
    // half randomly ?).
    // Or you can calculate the remainder separation days after minimum separation days, and split the remainder
    // in half

    private LocalDate[] getPaddingRangeForDepartureFlight(FlightSearchQueryDTO queryDTO, double separationFactor) {
        LocalDate earliestPossibleDeparture = LocalDate.now().plusDays(1);
        LocalDate earliestDepartureWithinPaddingRange = queryDTO.getDepartureDate().minusDays(paddingRange);

        int separationDays = (int) Math.round(separationFactor);

        LocalDate latestPossibleDeparture = queryDTO.getReturnDate().minusDays(separationDays);
        LocalDate latestDepartureWithinPaddingRange = queryDTO.getDepartureDate().plusDays(paddingRange);

        LocalDate trueEarliestDeparture = earliestPossibleDeparture.isBefore(earliestDepartureWithinPaddingRange)
                ? earliestDepartureWithinPaddingRange
                : earliestPossibleDeparture;

        LocalDate trueLatestDeparture = latestPossibleDeparture.isAfter(latestDepartureWithinPaddingRange)
                ? latestDepartureWithinPaddingRange
                : latestPossibleDeparture;

        return new LocalDate[]{trueEarliestDeparture, trueLatestDeparture};
    }

    private LocalDate[] getPaddingRangeForReturnFlight(
            FlightSearchQueryDTO queryDTO,
            double separationFactor,
            LocalDate departureFlightUpperLimitDate
    ) {
        int separationDays = (int) Math.round(separationFactor);
        LocalDate earliestPossibleReturn = queryDTO.getDepartureDate().plusDays(separationDays);

        LocalDate earliestPossibleReturnWithinLimits = earliestPossibleReturn.isBefore(departureFlightUpperLimitDate)
                ? departureFlightUpperLimitDate
                : earliestPossibleReturn;
        LocalDate earliestReturnWithinPaddingRange = queryDTO.getReturnDate().minusDays(paddingRange);

        LocalDate trueEarliestReturn = earliestPossibleReturnWithinLimits.isBefore(earliestReturnWithinPaddingRange)
                ? earliestReturnWithinPaddingRange
                : earliestPossibleReturnWithinLimits;
        LocalDate trueLatestReturn = queryDTO.getReturnDate().plusDays(paddingRange);
        return new LocalDate[]{trueEarliestReturn, trueLatestReturn};
    }


    Optional<FlightInstance> findFlightByPublicId(String publicId) {
        return flightInstanceRepository.findByPublicId(publicId);
    }

}
