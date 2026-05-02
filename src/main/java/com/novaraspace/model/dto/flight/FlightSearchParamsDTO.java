package com.novaraspace.model.dto.flight;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Objects;

public class FlightSearchParamsDTO {
    @NotBlank
    @Size(min = 3, max = 4)
    private String departureCode;
    @NotBlank
    @Size(min = 3, max = 4)
    private String arrivalCode;
    @NotNull
    @Future
    private LocalDate departureDate;
    private LocalDate returnDate;
    @Min(1)
    @Max(11)
    private int totalPaxCount;
    private boolean returnFlight;

    @AssertTrue(message = "Invalid date selection.")
    public boolean validReturnDate() {
        if (returnDate == null || departureDate == null || !returnFlight) { return true; }
        return returnDate.isAfter(departureDate);
    }

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
        return returnFlight && returnDate != null;
    }

    public FlightSearchParamsDTO setReturnFlight(boolean returnFlight) {
        this.returnFlight = returnFlight;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightSearchParamsDTO that = (FlightSearchParamsDTO) o;
        return totalPaxCount == that.totalPaxCount && returnFlight == that.returnFlight && Objects.equals(departureCode, that.departureCode) && Objects.equals(arrivalCode, that.arrivalCode) && Objects.equals(departureDate, that.departureDate) && Objects.equals(returnDate, that.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureCode, arrivalCode, departureDate, returnDate, totalPaxCount, returnFlight);
    }
}
