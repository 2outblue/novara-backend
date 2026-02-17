package com.novaraspace.model.domain;

import java.time.LocalDate;

public record PaddingRangeParams(
        LocalDate departureDate,
        LocalDate returnDate,
        int separationDays,
        LocalDate departureUpperLimitDate
) {
}
