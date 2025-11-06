package com.novaraspace.service;

import com.novaraspace.model.dto.flight.RouteAvailabilityRequestDTO;
import com.novaraspace.model.entity.FlightTemplate;
import com.novaraspace.model.exception.FlightException;
import com.novaraspace.repository.FlightInstanceRepository;
import com.novaraspace.repository.FlightTemplateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlightService {

    private final int availabilityMonthsFromToday = 6;

    private final FlightTemplateRepository flightTemplateRepository;
    private final FlightInstanceRepository flightInstanceRepository;
    private final LocationService locationService;

    public FlightService(FlightTemplateRepository flightTemplateRepository, FlightInstanceRepository flightInstanceRepository, LocationService locationService) {
        this.flightTemplateRepository = flightTemplateRepository;
        this.flightInstanceRepository = flightInstanceRepository;
        this.locationService = locationService;
    }


    public List<LocalDate> getRouteAvailability(String departureCode, String arrivalCode) {
        long dptId = locationService.getLocationIdByCode(departureCode);
        long arrId = locationService.getLocationIdByCode(arrivalCode);

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
}
