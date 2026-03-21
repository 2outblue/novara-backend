package com.novaraspace.model.enums.audit;

public enum BookingEventType {
    CREATE, CANCEL, FLIGHT_CHANGE;

    public AuditAction toAuditAction() {
        return switch (this) {
            case CREATE -> AuditAction.BOOKING_CREATE;
            case CANCEL -> AuditAction.BOOKING_CANCEL;
            case FLIGHT_CHANGE -> AuditAction.BOOKING_FLIGHT_CHANGE;
        };

    }
}
