package com.novaraspace.model.dto.flight;

import com.novaraspace.model.entity.FlightInstance;

import java.util.List;

public class FlightSearchResultDTO {
    private List<FlightUiDTO> departureFlights;
    private List<FlightUiDTO> returnFlights;
    private FlightLimitsDTO limits;
    private String bookingQuoteRef;

    public List<FlightUiDTO> getDepartureFlights() {
        return departureFlights;
    }

    public FlightSearchResultDTO setDepartureFlights(List<FlightUiDTO> departureFlights) {
        this.departureFlights = departureFlights;
        return this;
    }

    public List<FlightUiDTO> getReturnFlights() {
        return returnFlights;
    }

    public FlightSearchResultDTO setReturnFlights(List<FlightUiDTO> returnFlights) {
        this.returnFlights = returnFlights;
        return this;
    }

    public FlightLimitsDTO getLimits() {
        return limits;
    }

    public FlightSearchResultDTO setLimits(FlightLimitsDTO limits) {
        this.limits = limits;
        return this;
    }

    public String getBookingQuoteRef() {
        return bookingQuoteRef;
    }

    public FlightSearchResultDTO setBookingQuoteRef(String bookingQuoteRef) {
        this.bookingQuoteRef = bookingQuoteRef;
        return this;
    }
}
