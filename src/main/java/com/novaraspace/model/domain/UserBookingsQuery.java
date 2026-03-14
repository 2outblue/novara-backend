package com.novaraspace.model.domain;

import java.time.LocalDateTime;

public record UserBookingsQuery(
        long userId,
        LocalDateTime minDate,
        LocalDateTime maxDate
) {

}
