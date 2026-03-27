package com.novaraspace.web;

import com.novaraspace.model.dto.admin.*;
import com.novaraspace.model.dto.admin.publicAdmin.PaAuditLogDTO;
import com.novaraspace.model.dto.admin.publicAdmin.PaUserControlResult;
import com.novaraspace.model.dto.admin.publicAdmin.PaUserDetailsDTO;
import com.novaraspace.model.dto.audit.AuditLogDTO;
import com.novaraspace.model.dto.audit.AuditLogRequestDTO;
import com.novaraspace.model.other.PageResponse;
import com.novaraspace.service.PublicAdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public-admin")
@PreAuthorize("hasRole('PUBLIC_ADMIN')")
public class PublicAdminController {

    private final PublicAdminService adminService;

    public PublicAdminController(PublicAdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/panel")
    public ResponseEntity<AdminPanelDataResponse> getAdminPanelData(@Valid @RequestBody AdminPanelDataRequestDTO dto) {
        AdminPanelDataResponse response = adminService.getAdminPanelData(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/audit")
    public ResponseEntity<PageResponse<PaAuditLogDTO>> getAuditLogsPage(@Valid @RequestBody AuditLogRequestDTO req) {
        PageResponse<PaAuditLogDTO> response = adminService.getLogs(req);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/uc-search")
    public ResponseEntity<PageResponse<PaUserControlResult>> getUserControlSearchPage(@Valid @RequestBody UserControlSearchDTO req) {
        PageResponse<PaUserControlResult> res = adminService.getUserSearchPage(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/uc-details")
    public ResponseEntity<PaUserDetailsDTO> getUserDetails(@Valid @RequestBody UserDetailsRequestDTO req) {
        PaUserDetailsDTO res = adminService.getUserDetails(req);
        return ResponseEntity.ok(res);
    }
}
