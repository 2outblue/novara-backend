package com.novaraspace.web;

import com.novaraspace.model.dto.flight.*;
import com.novaraspace.model.other.PageResponse;
import com.novaraspace.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("flight")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/search")
    public ResponseEntity<FlightSearchResultDTO> getFlightSearchResult(@Valid @RequestBody FlightSearchParamsDTO flightSearchParamsDTO) {
        FlightSearchResultDTO resultDTO = flightService.getFlightSearchResult(flightSearchParamsDTO);
        return ResponseEntity.ok(resultDTO);
    }


    @PostMapping("/schedule")
    public ResponseEntity<PageResponse<FlightScheduleDTO>> getFlightsSchedule(@Valid @RequestBody FlightScheduleRequestDTO dto) {
        PageResponse<FlightScheduleDTO> response = flightService.getFlightsScheduleResponse(dto);
        return ResponseEntity.ok(response);
    }


}
