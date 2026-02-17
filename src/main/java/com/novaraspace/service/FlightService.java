package com.novaraspace.service;

import com.novaraspace.component.FlightLimitsGenerator;
import com.novaraspace.model.domain.FlightsWithinRangeRequest;
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
    private final FlightLimitsGenerator limitsGenerator;

    public FlightService(FlightTemplateRepository flightTemplateRepository, FlightInstanceRepository flightInstanceRepository, LocationService locationService, FlightMapper flightMapper, FlightLimitsGenerator limitsGenerator) {
        this.flightTemplateRepository = flightTemplateRepository;
        this.flightInstanceRepository = flightInstanceRepository;
        this.locationService = locationService;
        this.flightMapper = flightMapper;
        this.limitsGenerator = limitsGenerator;
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

        FlightLimitsDTO limits = limitsGenerator.generateLimits(queryDTO);

        FlightsWithinRangeRequest departureRequest = new FlightsWithinRangeRequest(
                departureTemplateIds,
                limits.getDepartureLowerDate(),
                limits.getDepartureUpperDate(),
                queryDTO.getTotalPaxCount());
        List<FlightUiDTO> departureFlights = getValidInstancesWithinRange(departureRequest)
                .stream().map(flightMapper::instanceToFlightUiDTO)
                .toList();

        FlightsWithinRangeRequest returnRequest = new FlightsWithinRangeRequest(
                returnTemplateIds,
                limits.getReturnLowerDate(),
                limits.getReturnUpperDate(),
                queryDTO.getTotalPaxCount());
        List<FlightUiDTO> returnFlights = getValidInstancesWithinRange(returnRequest)
                .stream().map(flightMapper::instanceToFlightUiDTO)
                .toList();

        if (departureFlights.isEmpty() || returnFlights.isEmpty()) {throw FlightException.noAvailability();}
        return new FlightSearchResultDTO()
                .setDepartureFlights(departureFlights)
                .setReturnFlights(returnFlights)
                .setLimits(limits);
    }

    private List<FlightInstance> getValidInstancesWithinRange(FlightsWithinRangeRequest request) {
        int paxCount = request.paxCount();
        return flightInstanceRepository
                .findAllForWithinRangeRequest(request)
                .stream().filter(fi -> {
                    return fi.getFirstClass().getAvailableSeats() >= paxCount
                            || fi.getMiddleClass().getAvailableSeats() >= paxCount
                            || fi.getLowerClass().getAvailableSeats() >= paxCount;
                }).toList();
    }

    Optional<FlightInstance> findFlightByPublicId(String publicId) {
        return flightInstanceRepository.findByPublicId(publicId);
    }

}
