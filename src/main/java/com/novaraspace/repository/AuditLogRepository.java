package com.novaraspace.repository;

import com.novaraspace.model.dto.audit.AuditLogRequestDTO;
import com.novaraspace.model.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {



    @Query("""
    select a from AuditLog a where a.timestamp >= :#{#params.startDate}
    and a.timestamp <= :#{#params.endDate} and a.outcome in :#{#params.outcome.toOutcomes()}
    and a.action in :#{#params.action.toAuditActions()}
    """)
    Page<AuditLog> getPage(@Param("params") AuditLogRequestDTO params, Pageable pageable);
}
