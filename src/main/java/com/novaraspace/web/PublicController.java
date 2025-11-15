package com.novaraspace.web;

import com.novaraspace.model.dto.flight.FlightSearchQueryDTO;
import com.novaraspace.model.dto.flight.FlightSearchResultDTO;
import com.novaraspace.model.dto.flight.RouteAvailabilityRequestDTO;
import com.novaraspace.model.dto.location.LocationGroupDTO;
import com.novaraspace.service.FlightService;
import com.novaraspace.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("public")
public class PublicController {

    private final LocationService locationService;
    private final FlightService flightService;

    public PublicController(LocationService locationService, FlightService flightService) {
        this.locationService = locationService;
        this.flightService = flightService;
    }

    @GetMapping("/location")
    public ResponseEntity<List<LocationGroupDTO>> getAllLocationGroups() {
        List<LocationGroupDTO> locationGroups = locationService.getAllLocationGroups();
        return ResponseEntity.ok(locationGroups);
    }

    @PostMapping("/flight-availability")
    public ResponseEntity<List<LocalDate>> getFlightAvailability(@RequestBody RouteAvailabilityRequestDTO dto) {
        List<LocalDate> availabilityDates = flightService.getRouteAvailability(dto.getDepartureCode(), dto.getArrivalCode());
        return ResponseEntity.ok(availabilityDates);
    }

    @PostMapping("/flight-search")
    public ResponseEntity<FlightSearchResultDTO> searchFlightsForQuery(@RequestBody FlightSearchQueryDTO queryDTO) {
        FlightSearchResultDTO result = flightService.searchFlightsForQuery(queryDTO);
        return ResponseEntity.ok(result);
    }
}
