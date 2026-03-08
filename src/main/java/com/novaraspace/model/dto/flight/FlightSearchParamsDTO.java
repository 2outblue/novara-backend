package com.novaraspace.model.dto.flight;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class FlightSearchParamsDTO {
    @NotNull
    @Size(min = 3, max = 4)
    private String departureCode;
    @NotNull
    @Size(min = 3, max = 4)
    private String arrivalCode;
    @NotNull
    @Future
    private LocalDate departureDate;
    private LocalDate returnDate;
    @Min(1)
    private int totalPaxCount;
    private boolean returnFlight;

    public String getDepartureCode() {
        return departureCode;
    }

    public FlightSearchParamsDTO setDepartureCode(String departureCode) {
        this.departureCode = departureCode;
        return this;
    }

    public String getArrivalCode() {
        return arrivalCode;
    }

    public FlightSearchParamsDTO setArrivalCode(String arrivalCode) {
        this.arrivalCode = arrivalCode;
        return this;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public FlightSearchParamsDTO setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public FlightSearchParamsDTO setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public int getTotalPaxCount() {
        return totalPaxCount;
    }

    public FlightSearchParamsDTO setTotalPaxCount(int totalPaxCount) {
        this.totalPaxCount = totalPaxCount;
        return this;
    }

    public boolean hasReturnFlight() {
        return returnFlight;
    }

    public FlightSearchParamsDTO setReturnFlight(boolean returnFlight) {
        this.returnFlight = returnFlight;
        return this;
    }
}
