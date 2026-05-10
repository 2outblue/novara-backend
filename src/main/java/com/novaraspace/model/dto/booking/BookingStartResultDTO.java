package com.novaraspace.model.dto.booking;

import com.novaraspace.model.dto.flight.FlightSearchResultDTO;

public class BookingStartResultDTO {
    private FlightSearchResultDTO flightSearchResult;
    private String quoteRef;
    private ServicesPricingOffer servicesPricing;

    public FlightSearchResultDTO getFlightSearchResult() {
        return flightSearchResult;
    }

    public BookingStartResultDTO setFlightSearchResult(FlightSearchResultDTO flightSearchResult) {
        this.flightSearchResult = flightSearchResult;
        return this;
    }

    public String getQuoteRef() {
        return quoteRef;
    }

    public BookingStartResultDTO setQuoteRef(String quoteRef) {
        this.quoteRef = quoteRef;
        return this;
    }

    public ServicesPricingOffer getServicesPricing() {
        return servicesPricing;
    }

    public BookingStartResultDTO setServicesPricing(ServicesPricingOffer servicesPricing) {
        this.servicesPricing = servicesPricing;
        return this;
    }
}
