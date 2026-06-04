package com.novaraspace.model.domain;

public record RefreshTokenParams(
        String publicKey,
        String rawToken
) {
}
