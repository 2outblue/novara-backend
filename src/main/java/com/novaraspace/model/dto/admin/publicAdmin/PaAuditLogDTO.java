package com.novaraspace.model.dto.admin.publicAdmin;

import com.novaraspace.model.enums.audit.AuditAction;
import com.novaraspace.model.enums.audit.AuditRole;
import com.novaraspace.model.enums.audit.AuditTargetType;
import com.novaraspace.model.enums.audit.Outcome;

import java.time.Instant;

public class PaAuditLogDTO {
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

    public PaAuditLogDTO setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Long getActorId() {
        return actorId;
    }

    public PaAuditLogDTO setActorId(Long actorId) {
        this.actorId = 0L;
        return this;
    }

    public String getActorEmail() {
        return actorEmail;
    }

    public PaAuditLogDTO setActorEmail(String actorEmail) {
        this.actorEmail = "****";
        return this;
    }

    public AuditRole getActorRole() {
        return actorRole;
    }

    public PaAuditLogDTO setActorRole(AuditRole actorRole) {
        this.actorRole = actorRole;
        return this;
    }

    public AuditAction getAction() {
        return action;
    }

    public PaAuditLogDTO setAction(AuditAction action) {
        this.action = action;
        return this;
    }

    public AuditTargetType getTargetType() {
        return targetType;
    }

    public PaAuditLogDTO setTargetType(AuditTargetType targetType) {
        this.targetType = targetType;
        return this;
    }

    public String getTargetDetails() {
        return targetDetails;
    }

    public PaAuditLogDTO setTargetDetails(String targetDetails) {
        this.targetDetails = "****";
        return this;
    }

    public Long getTargetId() {
        return targetId;
    }

    public PaAuditLogDTO setTargetId(Long targetId) {
        this.targetId = 0L;
        return this;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public PaAuditLogDTO setOutcome(Outcome outcome) {
        this.outcome = outcome;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public PaAuditLogDTO setDetails(String details) {
        this.details = details;
        return this;
    }
}
