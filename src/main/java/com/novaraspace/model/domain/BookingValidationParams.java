package com.novaraspace.model.domain;

import com.novaraspace.model.entity.Booking;
import com.novaraspace.model.entity.BookingQuote;
import com.novaraspace.model.entity.Payment;

public record BookingValidationParams(
        Booking booking,
        Payment payment,
        BookingQuote quote
) {
}
