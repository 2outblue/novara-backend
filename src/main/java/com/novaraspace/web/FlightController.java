package com.novaraspace.web;

import com.novaraspace.model.dto.flight.*;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.other.PageResponse;
import com.novaraspace.service.FlightService;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<FlightSearchResultDTO> getFlightSearchResult(@RequestBody FlightSearchQueryDTO flightSearchQueryDTO) {
        FlightSearchResultDTO resultDTO = flightService.getFlightSearchResult(flightSearchQueryDTO);
        return ResponseEntity.ok(resultDTO);
    }


    @PostMapping("/schedule")
    public ResponseEntity<PageResponse<FlightScheduleDTO>> getFlightsSchedule(@RequestBody FlightScheduleRequestDTO dto) {
        PageResponse<FlightScheduleDTO> response = flightService.getFlightsScheduleResponse(dto);
        return ResponseEntity.ok(response);
    }


}
