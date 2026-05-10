package com.novaraspace.model.domain;

import com.novaraspace.model.dto.booking.ServicesPricingOffer;
import com.novaraspace.model.dto.flight.FlightSearchParamsDTO;
import com.novaraspace.model.dto.flight.FlightSearchResultDTO;

public record BookingQuoteParams(
        FlightSearchParamsDTO flightSearchParams,
        FlightSearchResultDTO flightSearchResult,
        ServicesPricingOffer servicesPricing
) {
}
