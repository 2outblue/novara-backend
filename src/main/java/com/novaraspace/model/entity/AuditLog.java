package com.novaraspace.model.entity;

import com.novaraspace.model.enums.audit.AuditAction;
import com.novaraspace.model.enums.audit.Outcome;
import com.novaraspace.model.enums.audit.AuditRole;
import com.novaraspace.model.enums.audit.AuditTargetType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.Instant;

import static java.sql.Types.VARCHAR;

@Entity
public class AuditLog extends BaseEntity {
    @Column(nullable = false)
    private Instant timestamp;
    private Long actorId;
    private String actorEmail;
    @JdbcTypeCode(VARCHAR)
    private AuditRole actorRole;

    @Column(nullable = false)
    @JdbcTypeCode(VARCHAR)
    private AuditAction action;

    @JdbcTypeCode(VARCHAR)
    private AuditTargetType targetType;
    private String targetDetails;
    private Long targetId;

    @Column(nullable = false)
    @JdbcTypeCode(VARCHAR)
    private Outcome outcome;
    private String details;

    public AuditLog() {
        this.timestamp = Instant.now();
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public AuditLog setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Long getActorId() {
        return actorId;
    }

    public AuditLog setActorId(Long actorId) {
        this.actorId = actorId;
        return this;
    }

    public String getActorEmail() {
        return actorEmail;
    }

    public AuditLog setActorEmail(String actorEmail) {
        this.actorEmail = actorEmail;
        return this;
    }

    public AuditRole getActorRole() {
        return actorRole;
    }

    public AuditLog setActorRole(AuditRole actorRole) {
        this.actorRole = actorRole;
        return this;
    }

    public AuditAction getAction() {
        return action;
    }

    public AuditLog setAction(AuditAction action) {
        this.action = action;
        return this;
    }

    public AuditTargetType getTargetType() {
        return targetType;
    }

    public AuditLog setTargetType(AuditTargetType targetType) {
        this.targetType = targetType;
        return this;
    }

    public String getTargetDetails() {
        return targetDetails;
    }

    public AuditLog setTargetDetails(String targetDetails) {
        this.targetDetails = targetDetails;
        return this;
    }

    public Long getTargetId() {
        return targetId;
    }

    public AuditLog setTargetId(Long targetId) {
        this.targetId = targetId;
        return this;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public AuditLog setOutcome(Outcome status) {
        this.outcome = status;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public AuditLog setDetails(String details) {
        this.details = details;
        return this;
    }
}
