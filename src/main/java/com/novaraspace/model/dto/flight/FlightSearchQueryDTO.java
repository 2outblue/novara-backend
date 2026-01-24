package com.novaraspace.model.dto.flight;

import java.time.LocalDate;

public class FlightSearchQueryDTO {

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

    public FlightSearchQueryDTO setDepartureCode(String departureCode) {
        this.departureCode = departureCode;
        return this;
    }

    public String getArrivalCode() {
        return arrivalCode;
    }

    public FlightSearchQueryDTO setArrivalCode(String arrivalCode) {
        this.arrivalCode = arrivalCode;
        return this;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public FlightSearchQueryDTO setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public FlightSearchQueryDTO setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public int getTotalPaxCount() {
        return totalPaxCount;
    }

    public FlightSearchQueryDTO setTotalPaxCount(int totalPaxCount) {
        this.totalPaxCount = totalPaxCount;
        return this;
    }

    public boolean hasReturnFlight() {
        return returnFlight;
    }

    public FlightSearchQueryDTO setReturnFlight(boolean returnFlight) {
        this.returnFlight = returnFlight;
        return this;
    }
}
