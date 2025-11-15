package com.novaraspace.service;

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


    public FlightSearchResultDTO searchFlightsForQuery(FlightSearchQueryDTO queryDTO) {
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
        LocalDate[] returnFlightPaddingRange = getPaddingRangeForReturnFlight(queryDTO, netSeparationFactor);

        List<FlightUiDTO> departureFlights = getValidInstancesWithinRange(departureTemplateIds, departureFlightPaddingRange, queryDTO.getPaxCount())
                .stream().map(flightMapper::instanceToFlightUiDTO)
                .toList();

        List<FlightUiDTO> returnFlights = getValidInstancesWithinRange(returnTemplateIds, returnFlightPaddingRange, queryDTO.getPaxCount())
                .stream().map(flightMapper::instanceToFlightUiDTO)
                .toList();


//        List<FlightInstance> departureFlights = flightInstanceRepository
//                .findAllWithTemplateIdsAndWithinRange(departureTemplateIds, departureFlightPaddingRange[0], departureFlightPaddingRange[1])
//                .stream().filter(fi -> {
//                    int paxCount = queryDTO.getPaxCount();
//                    return fi.getFirstClass().getAvailableSeats() >= paxCount
//                            || fi.getMiddleClass().getAvailableSeats() >= paxCount
//                            || fi.getLowerClass().getAvailableSeats() >= paxCount;
//                }).toList();
//
//        List<FlightInstance> returnFlights = flightInstanceRepository
//                .findAllWithTemplateIdsAndWithinRange(returnTemplateIds, returnFlightPaddingRange[0], returnFlightPaddingRange[1])
//                .stream().filter(fi -> {
//                    int paxCount = queryDTO.getPaxCount();
//                    return fi.getFirstClass().getAvailableSeats() >= paxCount
//                            || fi.getMiddleClass().getAvailableSeats() >= paxCount
//                            || fi.getLowerClass().getAvailableSeats() >= paxCount;
//                }).toList();

        if (departureFlights.isEmpty() || returnFlights.isEmpty()) {throw FlightException.noAvailability();}

        return new FlightSearchResultDTO()
                .setDepartureFlights(departureFlights)
                .setReturnFlights(returnFlights);
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

//    private FlightUiDTO toFlightUiDTO(FlightInstance instance) {
//        FlightTemplate template = instance.getFlightTemplate();
//        Vehicle vehicle = template.getVehicle();
//
//        FlightUiDTO.FlightLeg departure = new FlightUiDTO.FlightLeg()
//                .setRegion(template.getDepartureLocation().getRegion())
//                .setLocation(template.getDepartureLocation().getName())
//                .setDate(instance.getDepartureDate())
//                .setMinimumOrbits(template.getOrbitsDeparture());
//
//        FlightUiDTO.FlightLeg arrival = new FlightUiDTO.FlightLeg()
//                .setRegion(template.getArrivalLocation().getRegion())
//                .setLocation(template.getArrivalLocation().getName())
//                .setDate(instance.getArrivalDate())
//                .setMinimumOrbits(template.getOrbitsArrival());
//
//        FlightUiDTO.CabinClassUi firstClass = new FlightUiDTO.CabinClassUi()
//                .setTotal(vehicle.getFirstClass().getTotalSeats())
//                .setAvailable(instance.getFirstClass().getAvailableSeats())
//                .setPrice(instance.getFirstClass().getBasePrice())
//                .setWindow(vehicle.getFirstClass().isWindowAvailable());
//
//        FlightUiDTO.CabinClassUi middleClass = new FlightUiDTO.CabinClassUi()
//                .setTotal(vehicle.getMiddleClass().getTotalSeats())
//                .setAvailable(instance.getMiddleClass().getAvailableSeats())
//                .setPrice(instance.getMiddleClass().getBasePrice())
//                .setWindow(vehicle.getMiddleClass().isWindowAvailable());
//
//        FlightUiDTO.CabinClassUi lowerClass = new FlightUiDTO.CabinClassUi()
//                .setTotal(vehicle.getLowerClass().getTotalSeats())
//                .setAvailable(instance.getLowerClass().getAvailableSeats())
//                .setPrice(instance.getLowerClass().getBasePrice())
//                .setWindow(vehicle.getLowerClass().isWindowAvailable());
//
//        FlightUiDTO.FlightCabinsUi cabins = new FlightUiDTO.FlightCabinsUi()
//                .setFirst(firstClass)
//                .setMiddle(middleClass)
//                .setLower(lowerClass);
//
//        return new FlightUiDTO()
//                .setId(instance.getPublicId())
//                .setStatus(instance.getStatus())
//                .setFlightNumber(template.getFlightNumber())
//                .setEva(vehicle.getAmenities().contains(VehicleAmenity.EVA))
//                .setDeparture(departure)
//                .setArrival(arrival)
//                .setTotalDurationMinutes(template.getDurationMinutes())
//                .setRequiredCertifs(new String[]{})
//                .setVehicleType(vehicle.getName())
//                .setAmenities(vehicle.getAmenities())
//                .setCabins(cabins)
//                .setTotalSpacesAvailable(instance.getTotalSeatsAvailable());
//
//    }


}
