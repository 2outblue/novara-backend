package com.novaraspace.model.enums.audit;

public enum PassEventType {
    RESET_REQUEST, CHANGE;

    public AuditAction toAuditAction() {
        return switch (this) {
            case RESET_REQUEST -> AuditAction.PASSWORD_RESET_REQUEST;
            case CHANGE -> AuditAction.PASSWORD_CHANGE;
        };
    }
}
