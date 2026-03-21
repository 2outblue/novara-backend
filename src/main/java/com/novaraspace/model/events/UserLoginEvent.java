package com.novaraspace.model.events;

import com.novaraspace.model.enums.audit.Outcome;

public record UserLoginEvent(
        Outcome outcome,
        String email
) { }
