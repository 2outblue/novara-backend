package com.novaraspace.model.dto.admin;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.Instant;

public class UserControlSearchDTO {
    @Min(0)
    private int page;
    @Max(50)
    private int size;
    @Min(1)
    private Long idFrom;
    @Min(1)
    private Long idTo;
    private Instant dateFrom;
    private Instant dateTo;
    @Email
    private String email;

    @AssertTrue(message = "Invalid date selection.")
    public boolean validFromAndToDates() {
        if (dateFrom == null || dateTo == null) { return true; }
        return dateFrom.isBefore(dateTo);
    }

    public int getPage() {
        return page;
    }

    public UserControlSearchDTO setPage(int page) {
        this.page = page;
        return this;
    }

    public int getSize() {
        return size;
    }

    public UserControlSearchDTO setSize(int size) {
        this.size = size;
        return this;
    }

    public Long getIdFrom() {
        return idFrom;
    }

    public UserControlSearchDTO setIdFrom(Long idFrom) {
        this.idFrom = idFrom;
        return this;
    }

    public Long getIdTo() {
        return idTo;
    }

    public UserControlSearchDTO setIdTo(Long idTo) {
        this.idTo = idTo;
        return this;
    }

    public Instant getDateFrom() {
        return dateFrom;
    }

    public UserControlSearchDTO setDateFrom(Instant dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public Instant getDateTo() {
        return dateTo;
    }

    public UserControlSearchDTO setDateTo(Instant dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserControlSearchDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
