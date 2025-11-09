package com.novaraspace.model.dto.flight;

import java.time.LocalDate;

public class FlightSearchQueryDTO {

    private String departureCode;
    private String arrivalCode;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private int paxCount;

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

    public int getPaxCount() {
        return paxCount;
    }

    public FlightSearchQueryDTO setPaxCount(int paxCount) {
        this.paxCount = paxCount;
        return this;
    }
}
