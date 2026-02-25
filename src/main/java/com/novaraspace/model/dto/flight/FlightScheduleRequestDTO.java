package com.novaraspace.model.dto.flight;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

public class FlightScheduleRequestDTO {
    @Max(100)
    @PositiveOrZero
    private int page;
    @Range(min = 1, max = 30)
    private int size;
    @NotEmpty
    private String departureLocationCode;
    @NotEmpty
    private String arrivalLocationCode;
    private LocalDate departureDate;

    public int getPage() {
        return page;
    }

    public FlightScheduleRequestDTO setPage(int page) {
        this.page = page;
        return this;
    }

    public int getSize() {
        return size;
    }

    public FlightScheduleRequestDTO setSize(int size) {
        this.size = size;
        return this;
    }

    public String getDepartureLocationCode() {
        return departureLocationCode;
    }

    public FlightScheduleRequestDTO setDepartureLocationCode(String departureLocationCode) {
        this.departureLocationCode = departureLocationCode;
        return this;
    }

    public String getArrivalLocationCode() {
        return arrivalLocationCode;
    }

    public FlightScheduleRequestDTO setArrivalLocationCode(String arrivalLocationCode) {
        this.arrivalLocationCode = arrivalLocationCode;
        return this;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public FlightScheduleRequestDTO setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
        return this;
    }
}
