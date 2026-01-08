package com.novaraspace.model.dto.booking;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingQuoteDTO {
    @NotBlank
    private String quoteId;
    @NotNull
    @Future
    private LocalDateTime expiresAt;

    private boolean oneWay;
    @Positive
    private int paxCount;

    @NotBlank
    private String departureCode;
    @NotBlank
    private String arrivalCode;
    @NotNull
    private LocalDate departureLowerDate;
    @NotNull
    private LocalDate departureUpperDate;
    @NotNull
    private LocalDate arrivalLowerDate;
    @NotNull
    private LocalDate arrivalUpperDate;

    public String getQuoteId() {
        return quoteId;
    }

    public BookingQuoteDTO setQuoteId(String quoteId) {
        this.quoteId = quoteId;
        return this;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public BookingQuoteDTO setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public BookingQuoteDTO setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
        return this;
    }

    public int getPaxCount() {
        return paxCount;
    }

    public BookingQuoteDTO setPaxCount(int paxCount) {
        this.paxCount = paxCount;
        return this;
    }

    public String getDepartureCode() {
        return departureCode;
    }

    public BookingQuoteDTO setDepartureCode(String departureCode) {
        this.departureCode = departureCode;
        return this;
    }

    public String getArrivalCode() {
        return arrivalCode;
    }

    public BookingQuoteDTO setArrivalCode(String arrivalCode) {
        this.arrivalCode = arrivalCode;
        return this;
    }

    public LocalDate getDepartureLowerDate() {
        return departureLowerDate;
    }

    public BookingQuoteDTO setDepartureLowerDate(LocalDate departureLowerDate) {
        this.departureLowerDate = departureLowerDate;
        return this;
    }

    public LocalDate getDepartureUpperDate() {
        return departureUpperDate;
    }

    public BookingQuoteDTO setDepartureUpperDate(LocalDate departureUpperDate) {
        this.departureUpperDate = departureUpperDate;
        return this;
    }

    public LocalDate getArrivalLowerDate() {
        return arrivalLowerDate;
    }

    public BookingQuoteDTO setArrivalLowerDate(LocalDate arrivalLowerDate) {
        this.arrivalLowerDate = arrivalLowerDate;
        return this;
    }

    public LocalDate getArrivalUpperDate() {
        return arrivalUpperDate;
    }

    public BookingQuoteDTO setArrivalUpperDate(LocalDate arrivalUpperDate) {
        this.arrivalUpperDate = arrivalUpperDate;
        return this;
    }
}
