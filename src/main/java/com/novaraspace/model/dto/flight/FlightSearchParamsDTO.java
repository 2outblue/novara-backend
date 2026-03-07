package com.novaraspace.model.dto.flight;

import java.time.LocalDate;

public class FlightSearchParamsDTO {

//    TODO: do validation on this
    private String departureCode;
    private String arrivalCode;
    private LocalDate departureDate;
    private LocalDate returnDate;
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
