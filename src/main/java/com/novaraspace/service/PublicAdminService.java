package com.novaraspace.service;

import com.novaraspace.component.DataMasker;
import com.novaraspace.model.dto.admin.*;
import com.novaraspace.model.dto.admin.publicAdmin.PaAuditLogDTO;
import com.novaraspace.model.dto.admin.publicAdmin.PaUserControlResult;
import com.novaraspace.model.dto.admin.publicAdmin.PaUserDetailsDTO;
import com.novaraspace.model.dto.audit.AuditLogDTO;
import com.novaraspace.model.dto.audit.AuditLogRequestDTO;
import com.novaraspace.model.enums.ErrCode;
import com.novaraspace.model.exception.UserException;
import com.novaraspace.model.mapper.PublicAdminMapper;
import com.novaraspace.model.other.PageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicAdminService {

    private final AdminService adminService;
    private final AuditLogService auditLogService;
    private final DataMasker dataMasker;
    private final PublicAdminMapper paMapper;


    public PublicAdminService(AdminService adminService, AuditLogService auditLogService, DataMasker dataMasker, PublicAdminMapper paMapper) {
        this.adminService = adminService;
        this.auditLogService = auditLogService;
        this.dataMasker = dataMasker;
        this.paMapper = paMapper;
    }


    public AdminPanelDataResponse getAdminPanelData(AdminPanelDataRequestDTO req) {
        AdminPanelDataResponse raw = adminService.getAdminPanelData(req);
        return new AdminPanelDataResponse()
                .setTotalUsers(raw.getTotalUsers())
                .setActiveUsers(maskEmails(raw.getActiveUsers()))
                .setUserLogins(maskEmails(raw.getUserLogins()))
                .setTotalLogins(raw.getTotalLogins())
                .setNewUsers(maskEmails(raw.getNewUsers()))
                .setStatusUsers(maskEmails(raw.getStatusUsers()));
    }


    public PageResponse<PaAuditLogDTO> getLogs(AuditLogRequestDTO req) {
        PageResponse<AuditLogDTO> rawPage = auditLogService.getLogs(req);
        List<PaAuditLogDTO> publicLogs = rawPage.getContent().stream()
                .map(paMapper::toPublicAuditLogDTO).toList();
        return new PageResponse<PaAuditLogDTO>()
                .setContent(publicLogs)
                .setPage(rawPage.getPage())
                .setSize(rawPage.getSize())
                .setTotalElements(rawPage.getTotalElements())
                .setTotalPages(rawPage.getTotalPages());
    }

    public PageResponse<PaUserControlResult> getUserSearchPage(UserControlSearchDTO req) {
        PageResponse<UserControlResult> rawPage = adminService.getUserSearchPage(req);
        List<PaUserControlResult> publicContent = rawPage.getContent().stream()
                .map(paMapper::toPaUserControlResult).toList();

        return new PageResponse<PaUserControlResult>()
                .setContent(publicContent)
                .setPage(rawPage.getPage())
                .setSize(rawPage.getSize())
                .setTotalElements(rawPage.getTotalElements())
                .setTotalPages(rawPage.getTotalPages());
    }

    public PaUserDetailsDTO getUserDetails(UserDetailsRequestDTO req) {
        req.setEmail("");
        if (req.getId() == null) {
            throw new UserException(ErrCode.BAD_REQUEST, HttpStatus.UNAUTHORIZED, "Public admin can't perform email search");
        }
        UserDetailsDTO rawDetails = adminService.getUserDetails(req);
        return paMapper.toPaUserDetailsDTO(rawDetails);
    }




    private EmailsAndTotalCount maskEmails(EmailsAndTotalCount raw) {
        List<String> maskedEmails = raw.getEmails().stream()
                .map(dataMasker::hardMaskEmail).toList();

        return new EmailsAndTotalCount()
                .setTotalCount(raw.getTotalCount())
                .setEmails(maskedEmails);
    }
}
