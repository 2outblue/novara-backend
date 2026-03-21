package com.novaraspace.model.dto.audit;

import com.novaraspace.model.enums.audit.AuditActionFilter;
import com.novaraspace.model.enums.audit.Outcome;
import com.novaraspace.model.enums.audit.OutcomeFilter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.Instant;

public class AuditLogRequestDTO {
    @Max(100)
    @PositiveOrZero
    private int page;
    @NotNull
    //TODO: Need validation for these dates - min and max
    private Instant startDate;
    @NotNull
    private Instant endDate;
    @NotNull
    private OutcomeFilter outcome;
    @NotNull
    private AuditActionFilter action;

    public int getPage() {
        return page;
    }

    public AuditLogRequestDTO setPage(int page) {
        this.page = page;
        return this;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public AuditLogRequestDTO setStartDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public AuditLogRequestDTO setEndDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public OutcomeFilter getOutcome() {
        return outcome;
    }

    public AuditLogRequestDTO setOutcome(OutcomeFilter outcome) {
        this.outcome = outcome;
        return this;
    }

    public AuditActionFilter getAction() {
        return action;
    }

    public AuditLogRequestDTO setAction(AuditActionFilter action) {
        this.action = action;
        return this;
    }
}
