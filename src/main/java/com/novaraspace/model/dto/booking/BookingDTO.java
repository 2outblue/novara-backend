package com.novaraspace.model.dto.booking;

import com.novaraspace.model.dto.flight.BookedFlightDTO;

import java.util.List;

public class BookingDTO {
    private String reference;
    private boolean oneWay;
    private BookedFlightDTO departureFlight;
    private BookedFlightDTO returnFlight;
    private List<PassengerDTO> passengers;
    private String contactEmailMasked;
    private String contactMobileMasked;
    private List<ExtraServiceDTO> extraServices;
    private boolean cancelled;
    private double minimumSeparationDays = 0;

    public String getReference() {
        return reference;
    }

    public BookingDTO setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public BookingDTO setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
        return this;
    }

    public BookedFlightDTO getDepartureFlight() {
        return departureFlight;
    }

    public BookingDTO setDepartureFlight(BookedFlightDTO departureFlight) {
        this.departureFlight = departureFlight;
        return this;
    }

    public BookedFlightDTO getReturnFlight() {
        return returnFlight;
    }

    public BookingDTO setReturnFlight(BookedFlightDTO returnFlight) {
        this.returnFlight = returnFlight;
        return this;
    }

    public List<PassengerDTO> getPassengers() {
        return passengers;
    }

    public BookingDTO setPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
        return this;
    }

    public String getContactEmailMasked() {
        return contactEmailMasked;
    }

    public BookingDTO setContactEmailMasked(String contactEmailMasked) {
        this.contactEmailMasked = contactEmailMasked;
        return this;
    }

    public String getContactMobileMasked() {
        return contactMobileMasked;
    }

    public BookingDTO setContactMobileMasked(String contactMobileMasked) {
        this.contactMobileMasked = contactMobileMasked;
        return this;
    }

    public List<ExtraServiceDTO> getExtraServices() {
        return extraServices;
    }

    public BookingDTO setExtraServices(List<ExtraServiceDTO> extraServices) {
        this.extraServices = extraServices;
        return this;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public BookingDTO setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
        return this;
    }

    public double getMinimumSeparationDays() {
        return minimumSeparationDays;
    }

    public BookingDTO setMinimumSeparationDays(double minimumSeparationDays) {
        this.minimumSeparationDays = minimumSeparationDays;
        return this;
    }
}
