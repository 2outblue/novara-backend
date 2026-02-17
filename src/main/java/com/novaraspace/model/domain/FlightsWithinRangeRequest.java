package com.novaraspace.model.domain;

import java.time.LocalDate;
import java.util.List;

public record FlightsWithinRangeRequest(
        List<Long> templateIds,
        LocalDate startDate,
        LocalDate endDate,
        int paxCount
) {
}
