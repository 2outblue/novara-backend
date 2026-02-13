package com.novaraspace.model.dto.booking;

import com.novaraspace.model.dto.flight.FlightReserveDTO;
import com.novaraspace.model.dto.payment.NewPaymentDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ChangeFlightsRequest {
    @Valid
    @NotNull
    private BookingSearchParams bookingParams;
    @Valid
    @NotNull
    private NewPaymentDTO payment;
    @Valid
    @NotNull
    private FlightReserveDTO departureFlight;
    @Valid
    private FlightReserveDTO returnFlight;
    @NotEmpty
    private String quoteReference;

    public @Valid BookingSearchParams getBookingParams() {
        return bookingParams;
    }

    public ChangeFlightsRequest setBookingParams(@Valid BookingSearchParams bookingParams) {
        this.bookingParams = bookingParams;
        return this;
    }

    public NewPaymentDTO getPayment() {
        return payment;
    }

    public ChangeFlightsRequest setPayment(NewPaymentDTO payment) {
        this.payment = payment;
        return this;
    }

    public String getQuoteReference() {
        return quoteReference;
    }

    public ChangeFlightsRequest setQuoteReference(String quoteReference) {
        this.quoteReference = quoteReference;
        return this;
    }

    public FlightReserveDTO getDepartureFlight() {
        return departureFlight;
    }

    public ChangeFlightsRequest setDepartureFlight(FlightReserveDTO departureFlight) {
        this.departureFlight = departureFlight;
        return this;
    }

    public FlightReserveDTO getReturnFlight() {
        return returnFlight;
    }

    public ChangeFlightsRequest setReturnFlight(FlightReserveDTO returnFlight) {
        this.returnFlight = returnFlight;
        return this;
    }
}
