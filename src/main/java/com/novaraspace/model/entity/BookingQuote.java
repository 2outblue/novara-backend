package com.novaraspace.model.entity;

import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

//TODO: need a scheduled task to clean these up if expired
@Entity
public class BookingQuote extends BaseEntity {
    private String reference;
    private LocalDateTime expiresAt;

    private boolean oneWay;
    private int paxCount;

    private String departureCode;
    private String arrivalCode;
    private LocalDate departureLowerDate;
    private LocalDate departureUpperDate;
    private LocalDate returnLowerDate; //TODO: WHY ARRIVAL ??? THIS IS **RETURN DATE** - Nothing to do with the arrival...?
    private LocalDate returnUpperDate;

    public String getReference() {
        return reference;
    }

    public BookingQuote setReference(String quoteId) {
        this.reference = quoteId;
        return this;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public BookingQuote setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }


    public boolean isOneWay() {
        return oneWay;
    }

    public BookingQuote setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
        return this;
    }

    public int getPaxCount() {
        return paxCount;
    }

    public BookingQuote setPaxCount(int paxCount) {
        this.paxCount = paxCount;
        return this;
    }

    public String getDepartureCode() {
        return departureCode;
    }

    public BookingQuote setDepartureCode(String departureCode) {
        this.departureCode = departureCode;
        return this;
    }

    public String getArrivalCode() {
        return arrivalCode;
    }

    public BookingQuote setArrivalCode(String arrivalCode) {
        this.arrivalCode = arrivalCode;
        return this;
    }

    public LocalDate getDepartureLowerDate() {
        return departureLowerDate;
    }

    public BookingQuote setDepartureLowerDate(LocalDate departureLowerDate) {
        this.departureLowerDate = departureLowerDate;
        return this;
    }

    public LocalDate getDepartureUpperDate() {
        return departureUpperDate;
    }

    public BookingQuote setDepartureUpperDate(LocalDate departureUpperDate) {
        this.departureUpperDate = departureUpperDate;
        return this;
    }

    public LocalDate getReturnLowerDate() {
        return returnLowerDate;
    }

    public BookingQuote setReturnLowerDate(LocalDate arrivalLowerDate) {
        this.returnLowerDate = arrivalLowerDate;
        return this;
    }

    public LocalDate getReturnUpperDate() {
        return returnUpperDate;
    }

    public BookingQuote setReturnUpperDate(LocalDate arrivalUpperDate) {
        this.returnUpperDate = arrivalUpperDate;
        return this;
    }
}
