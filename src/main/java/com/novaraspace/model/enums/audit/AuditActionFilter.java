package com.novaraspace.model.enums.audit;

import java.util.*;

public enum AuditActionFilter {

    ALL, ALL_USER_EVENTS, LOGIN, LOGOUT, REGISTER, PASSWORD_EVENT, BOOKING_EVENT, SYSTEM_EVENTS;

    public Set<AuditAction> toAuditActions() {
        List<AuditAction> list = switch (this) {
            case ALL -> List.of(AuditAction.values());
            case ALL_USER_EVENTS -> Arrays.asList(AuditAction.LOGIN,
                    AuditAction.LOGOUT, AuditAction.REGISTER, AuditAction.PASSWORD_RESET_REQUEST,
                    AuditAction.PASSWORD_CHANGE, AuditAction.UPDATE_USER, AuditAction.CHANGE_USER_STATUS);
            case LOGIN -> Arrays.asList(AuditAction.LOGIN);
            case LOGOUT -> Arrays.asList(AuditAction.LOGOUT);
            case REGISTER -> Arrays.asList(AuditAction.REGISTER);
            case PASSWORD_EVENT -> Arrays.asList(AuditAction.PASSWORD_RESET_REQUEST, AuditAction.PASSWORD_CHANGE);
            case BOOKING_EVENT -> Arrays.asList(AuditAction.BOOKING_CANCEL, AuditAction.BOOKING_CREATE,
                    AuditAction.BOOKING_FLIGHT_CHANGE);
            case SYSTEM_EVENTS -> Arrays.asList(AuditAction.SCHEDULED_TASK);

        };
        return new HashSet<>(list);
    }
}
