package com.novaraspace.repository;

import com.novaraspace.model.dto.audit.AuditLogRequestDTO;
import com.novaraspace.model.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {



    @Query("""
    select a from AuditLog a where a.timestamp >= :#{#params.startDate}
    and a.timestamp <= :#{#params.endDate}
    and a.action in :#{#params.action.toAuditActions()}
    and a.outcome in :#{#params.outcome.toOutcomes()}
    and (:#{#params.userId} is null or a.actorId = :#{#params.userId})
    """)
    Page<AuditLog> getPage(@Param("params") AuditLogRequestDTO params, Pageable pageable);

    @Query("select count(a) from AuditLog a where a.timestamp >= :minDate and a.action = 'LOGIN' and a.outcome = 'SUCCESS'")
    Long countTotalLoginsAfter(@Param("minDate") Instant minDate);

    @Query("select count(a) from AuditLog a where a.actorId = :id and a.action = 'LOGIN' and a.outcome = 'SUCCESS'")
    Long countLoginsForUser(@Param("id") Long actorId);
}
