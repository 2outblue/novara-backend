package com.novaraspace.web;

import com.novaraspace.factory.FlightJSONFactory;
import com.novaraspace.model.dto.admin.ActiveUsersResponseDTO;
import com.novaraspace.model.dto.flight.FlightTemplateGenerationRequest;
import com.novaraspace.model.other.FlightJSON;
import com.novaraspace.service.AdminService;
import com.novaraspace.service.FlightGenerationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class AdminController {

    private final FlightGenerationService flightGenerationService;
    private final AdminService adminService;

    public AdminController(FlightGenerationService flightGenerationService, AdminService adminService) {
        this.flightGenerationService = flightGenerationService;
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<Void> test() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/flight-json")
    public ResponseEntity<FlightJSON> generateFlightJSON(@RequestBody FlightTemplateGenerationRequest data) {
        FlightJSON fl = flightGenerationService.generateNewFlightJSON(data);
        return ResponseEntity.ok(fl);
    }


    @GetMapping("/active-users")
    public ResponseEntity<ActiveUsersResponseDTO> getCurrentActiveUsers() {
        ActiveUsersResponseDTO res = adminService.getCurrentActiveUsers();
        return ResponseEntity.ok(res);
    }


}
