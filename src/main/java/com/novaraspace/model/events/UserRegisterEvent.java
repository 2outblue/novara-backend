package com.novaraspace.model.events;

public record UserRegisterEvent(
        Long userId,
        String userEmail
) {}
