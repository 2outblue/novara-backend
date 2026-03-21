package com.novaraspace.model.dto.audit;

import com.novaraspace.model.enums.audit.AuditAction;
import com.novaraspace.model.enums.audit.AuditRole;
import com.novaraspace.model.enums.audit.AuditTargetType;
import com.novaraspace.model.enums.audit.Outcome;

import java.time.Instant;



public class AuditLogDTO {
    private Instant timestamp;
    private Long actorId;
    private String actorEmail;

    private AuditRole actorRole;
    private AuditAction action;

    private AuditTargetType targetType;
    private String targetDetails;
    private Long targetId;

    private Outcome outcome;
    private String details;

    public Instant getTimestamp() {
        return timestamp;
    }

    public AuditLogDTO setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Long getActorId() {
        return actorId;
    }

    public AuditLogDTO setActorId(Long actorId) {
        this.actorId = actorId;
        return this;
    }

    public String getActorEmail() {
        return actorEmail;
    }

    public AuditLogDTO setActorEmail(String actorEmail) {
        this.actorEmail = actorEmail;
        return this;
    }

    public AuditRole getActorRole() {
        return actorRole;
    }

    public AuditLogDTO setActorRole(AuditRole actorRole) {
        this.actorRole = actorRole;
        return this;
    }

    public AuditAction getAction() {
        return action;
    }

    public AuditLogDTO setAction(AuditAction action) {
        this.action = action;
        return this;
    }

    public AuditTargetType getTargetType() {
        return targetType;
    }

    public AuditLogDTO setTargetType(AuditTargetType targetType) {
        this.targetType = targetType;
        return this;
    }

    public String getTargetDetails() {
        return targetDetails;
    }

    public AuditLogDTO setTargetDetails(String targetDetails) {
        this.targetDetails = targetDetails;
        return this;
    }

    public Long getTargetId() {
        return targetId;
    }

    public AuditLogDTO setTargetId(Long targetId) {
        this.targetId = targetId;
        return this;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public AuditLogDTO setOutcome(Outcome outcome) {
        this.outcome = outcome;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public AuditLogDTO setDetails(String details) {
        this.details = details;
        return this;
    }
}
