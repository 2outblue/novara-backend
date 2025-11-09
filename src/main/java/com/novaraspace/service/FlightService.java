package com.novaraspace.service;

import com.novaraspace.model.dto.flight.FlightSearchQueryDTO;
import com.novaraspace.model.dto.flight.RouteAvailabilityRequestDTO;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.entity.FlightTemplate;
import com.novaraspace.model.entity.Location;
import com.novaraspace.model.enums.FlightRegion;
import com.novaraspace.model.exception.FlightException;
import com.novaraspace.repository.FlightInstanceRepository;
import com.novaraspace.repository.FlightTemplateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlightService {

    private final int availabilityMonthsFromToday = 6;

    @Value("${app.flight.padding-days-on-fetch}")
    private int paddingRange;

    private final FlightTemplateRepository flightTemplateRepository;
    private final FlightInstanceRepository flightInstanceRepository;
    private final LocationService locationService;

    public FlightService(FlightTemplateRepository flightTemplateRepository, FlightInstanceRepository flightInstanceRepository, LocationService locationService) {
        this.flightTemplateRepository = flightTemplateRepository;
        this.flightInstanceRepository = flightInstanceRepository;
        this.locationService = locationService;
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

//        Map<Integer, List<LocalDate>> availabilityByMonthMap = new HashMap<>();
//        for (LocalDate date : allDistinctAvailabilityDates) {
//            int month = date.getMonthValue();
//            if (!availabilityByMonthMap.containsKey(month)) {
//                availabilityByMonthMap.put(month, new ArrayList<>());
//            }
//            availabilityByMonthMap.get(month).add(date);
//        }
//        return availabilityByMonthMap;
    }


    public void searchFlightsForQuery(FlightSearchQueryDTO queryDTO) {
        Location departureLocation = locationService.getLocationByCode(queryDTO.getDepartureCode());
        Location arrivalLocation = locationService.getLocationByCode(queryDTO.getArrivalCode());

        List<Long> departureTemplateIds = flightTemplateRepository
                .findAllByDepartureAndArrivalLocationIds(departureLocation.getId(), arrivalLocation.getId())
                .stream().map(fi -> fi.getId()).toList();

        List<Long> returnTemplateIds = flightTemplateRepository
                .findAllByDepartureAndArrivalLocationIds(arrivalLocation.getId(), departureLocation.getId())
                .stream().map(fi -> fi.getId()).toList();

        double netSeparationFactor = Math.abs(departureLocation.getRegion().getSeparationFactor() - arrivalLocation.getRegion().getSeparationFactor());
        LocalDate[] departureFlightPaddingRange = getPaddingRangeForDepartureFlight(queryDTO, netSeparationFactor);
        LocalDate[] returnFlightPaddingRange = getPaddingRangeForReturnFlight(queryDTO, netSeparationFactor);

        List<FlightInstance> departureFlights = flightInstanceRepository.findAllWithTemplateIdsAndWithinRange(
                departureTemplateIds,
                departureFlightPaddingRange[0],
                departureFlightPaddingRange[1]);

        List<FlightInstance> returnFlights = flightInstanceRepository.findAllWithTemplateIdsAndWithinRange(
                returnTemplateIds,
                returnFlightPaddingRange[0],
                returnFlightPaddingRange[1]);

        // Filter the flights for pax count,
        // If no flights on either departure or return - throw error
        // Return them
        // Let the frontend derive the fareNoFare data from the returned flights.


    }

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

    private LocalDate[] getPaddingRangeForReturnFlight(FlightSearchQueryDTO queryDTO, double separationFactor) {
        int separationDays = (int) Math.round(separationFactor);
        LocalDate earliestPossibleReturn = queryDTO.getDepartureDate().plusDays(separationDays);
        LocalDate earliestReturnWithinPaddingRange = queryDTO.getReturnDate().minusDays(paddingRange);

        LocalDate trueEarliestReturn = earliestPossibleReturn.isBefore(earliestReturnWithinPaddingRange)
                ? earliestReturnWithinPaddingRange
                : earliestPossibleReturn;
        LocalDate trueLatestReturn = queryDTO.getReturnDate().plusDays(paddingRange);
        return new LocalDate[]{trueEarliestReturn, trueLatestReturn};
    }


}
