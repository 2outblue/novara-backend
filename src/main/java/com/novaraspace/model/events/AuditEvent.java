package com.novaraspace.model.events;

import com.novaraspace.model.enums.audit.AuditAction;
import com.novaraspace.model.enums.audit.AuditRole;
import com.novaraspace.model.enums.audit.AuditTargetType;
import com.novaraspace.model.enums.audit.Outcome;
import jakarta.persistence.Column;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.Instant;

import static java.sql.Types.VARCHAR;

public abstract class AuditEvent {
    private Instant timestamp = Instant.now();
    private Long actorId = null;
    private String actorEmail = null;
    private AuditRole actorRole = null;

    private AuditAction action = null;

    private AuditTargetType targetType = null;
    private String targetDetails = null;
    private Long targetId = null;

    private Outcome outcome = null;
    private String details = null;

    public AuditEvent() {}

    public Instant getTimestamp() {
        return timestamp;
    }

    public AuditEvent setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Long getActorId() {
        return actorId;
    }

    public AuditEvent setActorId(Long actorId) {
        this.actorId = actorId;
        return this;
    }

    public String getActorEmail() {
        return actorEmail;
    }

    public AuditEvent setActorEmail(String actorEmail) {
        this.actorEmail = actorEmail;
        return this;
    }

    public AuditRole getActorRole() {
        return actorRole;
    }

    public AuditEvent setActorRole(AuditRole actorRole) {
        this.actorRole = actorRole;
        return this;
    }

    public AuditAction getAction() {
        return action;
    }

    public AuditEvent setAction(AuditAction action) {
        this.action = action;
        return this;
    }

    public AuditTargetType getTargetType() {
        return targetType;
    }

    public AuditEvent setTargetType(AuditTargetType targetType) {
        this.targetType = targetType;
        return this;
    }

    public String getTargetDetails() {
        return targetDetails;
    }

    public AuditEvent setTargetDetails(String targetDetails) {
        this.targetDetails = targetDetails;
        return this;
    }

    public Long getTargetId() {
        return targetId;
    }

    public AuditEvent setTargetId(long targetId) {
        this.targetId = targetId;
        return this;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public AuditEvent setOutcome(Outcome outcome) {
        this.outcome = outcome;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public AuditEvent setDetails(String details) {
        this.details = details;
        return this;
    }
}
