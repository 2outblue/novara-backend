package com.novaraspace.model.dto.booking;

import com.novaraspace.model.dto.flight.FlightSearchResultDTO;

public class ChangeFlightsStartResponse {
    private FlightSearchResultDTO flightSearchResult;
    private String quoteReference; //Mainly used to validate flight dates

    public FlightSearchResultDTO getFlightSearchResult() {
        return flightSearchResult;
    }

    public ChangeFlightsStartResponse setFlightSearchResult(FlightSearchResultDTO flightSearchResult) {
        this.flightSearchResult = flightSearchResult;
        return this;
    }

    public String getQuoteReference() {
        return quoteReference;
    }

    public ChangeFlightsStartResponse setQuoteReference(String quoteReference) {
        this.quoteReference = quoteReference;
        return this;
    }
}
