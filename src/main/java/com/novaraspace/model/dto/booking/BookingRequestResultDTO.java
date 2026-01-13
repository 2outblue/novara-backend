package com.novaraspace.model.dto.booking;

import com.novaraspace.model.dto.flight.FlightSearchResultDTO;

public class BookingRequestResultDTO {
    private FlightSearchResultDTO flightSearchResult;
    private String quoteRef;

    public FlightSearchResultDTO getFlightSearchResult() {
        return flightSearchResult;
    }

    public BookingRequestResultDTO setFlightSearchResult(FlightSearchResultDTO flightSearchResult) {
        this.flightSearchResult = flightSearchResult;
        return this;
    }

    public String getQuoteRef() {
        return quoteRef;
    }

    public BookingRequestResultDTO setQuoteRef(String quoteRef) {
        this.quoteRef = quoteRef;
        return this;
    }
}
