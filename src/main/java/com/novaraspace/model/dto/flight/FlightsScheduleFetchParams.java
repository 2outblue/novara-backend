package com.novaraspace.model.dto.flight;

import java.time.LocalDate;
import java.util.List;

public record FlightsScheduleFetchParams(
        List<Long> templates,
        LocalDate departureDate,
        LocalDate departureDateMax
) {
}
