package com.novaraspace.model.domain;

public record PassResetEmailParams(
        String email,
        String tokenValue,
        String userFirstName,
        String expiryMinutes
) {
}
