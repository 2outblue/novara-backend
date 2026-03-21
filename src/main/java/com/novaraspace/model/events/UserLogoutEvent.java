package com.novaraspace.model.events;

public record UserLogoutEvent(
        Long userId,
        String userEmail
) {
}
