package com.novaraspace.model.domain;

import java.time.LocalDate;

public record PaddingRangeResult(
        LocalDate earliestDate,
        LocalDate latestDate
) {
}
