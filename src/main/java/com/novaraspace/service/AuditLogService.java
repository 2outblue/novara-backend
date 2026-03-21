package com.novaraspace.service;

import com.novaraspace.model.dto.audit.AuditLogDTO;
import com.novaraspace.model.dto.audit.AuditLogRequestDTO;
import com.novaraspace.model.entity.AuditLog;
import com.novaraspace.model.mapper.AuditLogMapper;
import com.novaraspace.model.other.PageResponse;
import com.novaraspace.repository.AuditLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    public AuditLogService(AuditLogRepository auditLogRepository, AuditLogMapper auditLogMapper) {
        this.auditLogRepository = auditLogRepository;
        this.auditLogMapper = auditLogMapper;
    }


    public PageResponse<AuditLogDTO> getLogs(AuditLogRequestDTO req) {
        Pageable pageable = PageRequest.of(
                req.getPage(),
                50,
                Sort.by("timestamp").ascending());
        Page<AuditLog> page = auditLogRepository.getPage(req, pageable);
        List<AuditLogDTO> dtos = page.getContent().stream().map(auditLogMapper::entityToDto).toList();

        return new PageResponse<AuditLogDTO>()
                .setContent(dtos)
                .setPage(page.getNumber())
                .setSize(page.getSize())
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages());
    }


}
