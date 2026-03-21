package com.novaraspace.model.events;

import com.novaraspace.model.enums.audit.BookingEventType;

public record BookingEvent(
        BookingEventType type,
        Long id,
        String reference
) {
}
