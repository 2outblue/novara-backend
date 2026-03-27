package com.novaraspace.model.mapper;

import com.novaraspace.model.dto.admin.UserControlResult;
import com.novaraspace.model.dto.admin.UserDetailsDTO;
import com.novaraspace.model.dto.admin.publicAdmin.PaAuditLogDTO;
import com.novaraspace.model.dto.admin.publicAdmin.PaUserControlResult;
import com.novaraspace.model.dto.admin.publicAdmin.PaUserDetailsDTO;
import com.novaraspace.model.dto.audit.AuditLogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PublicAdminMapper {

    public abstract PaAuditLogDTO toPublicAuditLogDTO(AuditLogDTO rawDTO);

    public abstract PaUserControlResult toPaUserControlResult(UserControlResult raw);

    public abstract PaUserDetailsDTO toPaUserDetailsDTO(UserDetailsDTO raw);

}
