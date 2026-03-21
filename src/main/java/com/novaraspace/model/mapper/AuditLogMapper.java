package com.novaraspace.model.mapper;

import com.novaraspace.model.dto.audit.AuditLogDTO;
import com.novaraspace.model.entity.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AuditLogMapper {

    public abstract AuditLogDTO entityToDto(AuditLog auditLog);
}
