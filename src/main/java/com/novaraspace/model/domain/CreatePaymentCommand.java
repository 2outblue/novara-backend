package com.novaraspace.model.domain;

public record CreatePaymentCommand(
        String serviceReference,
        String lastFour,
        String cardHolder,
        String email,
        String phoneNumber
) {
}
