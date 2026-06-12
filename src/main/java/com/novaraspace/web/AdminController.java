package com.novaraspace.web;

import com.novaraspace.model.dto.admin.*;
import com.novaraspace.model.dto.audit.AuditLogDTO;
import com.novaraspace.model.dto.audit.AuditLogRequestDTO;
import com.novaraspace.model.dto.flight.FlightInstanceGenerationParams;
import com.novaraspace.model.dto.flight.FlightTemplateGenerationRequest;
import com.novaraspace.model.other.FlightJSON;
import com.novaraspace.model.other.PageResponse;
import com.novaraspace.service.AdminService;
import com.novaraspace.service.AuditLogService;
import com.novaraspace.service.FlightGenerationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class AdminController {

    private final FlightGenerationService flightGenerationService;
    private final AdminService adminService;
    private final AuditLogService auditLogService;

    public AdminController(FlightGenerationService flightGenerationService, AdminService adminService, AuditLogService auditLogService) {
        this.flightGenerationService = flightGenerationService;
        this.adminService = adminService;
        this.auditLogService = auditLogService;
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

    @PostMapping("/panel")
    public ResponseEntity<AdminPanelDataResponse> getAdminPanelData(@Valid @RequestBody AdminPanelDataRequestDTO dto) {
        AdminPanelDataResponse response = adminService.getAdminPanelData(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/audit")
    public ResponseEntity<PageResponse<AuditLogDTO>> getAuditLogsPage(@Valid @RequestBody AuditLogRequestDTO req) {
        PageResponse<AuditLogDTO> response = auditLogService.getLogs(req);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/uc-search")
    public ResponseEntity<PageResponse<UserControlResult>> getUserControlSearchPage(@Valid @RequestBody UserControlSearchDTO req) {
        PageResponse<UserControlResult> res = adminService.getUserSearchPage(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/uc-details")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@Valid @RequestBody UserDetailsRequestDTO req) {
        UserDetailsDTO res = adminService.getUserDetails(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/uc-status-change")
    public ResponseEntity<UserDetailsDTO> changeUserStatus(@Valid @RequestBody ChangeUserStatusRequestDTO req) {
        UserDetailsDTO res = adminService.changeUserStatus(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/uc-reset-pass")
    public ResponseEntity<Void> resetUserPassword(@Valid @RequestBody UcPasswordResetRequest req) {
        adminService.resetUserPassword(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/generate-flights")
    public ResponseEntity<Integer> generateFlights(@RequestBody @Valid FlightInstanceGenerationParams params) {
        Integer count = flightGenerationService.generateForAllTemplates(params);
        return ResponseEntity.ok(count);
    }

}
