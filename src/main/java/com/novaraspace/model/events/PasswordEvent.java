package com.novaraspace.model.events;

import com.novaraspace.model.enums.audit.PassEventType;

public record PasswordEvent(
        PassEventType type,
        String email
) {
}
