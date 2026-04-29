package com.novaraspace.model.dto.flight;

import com.novaraspace.validation.annotations.ValidFlightGenParams;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;

@ValidFlightGenParams
public class FlightInstanceGenerationParams {
    private LocalDate startDate;
    private LocalDate endDate;

    public FlightInstanceGenerationParams(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public FlightInstanceGenerationParams setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public FlightInstanceGenerationParams setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
}
