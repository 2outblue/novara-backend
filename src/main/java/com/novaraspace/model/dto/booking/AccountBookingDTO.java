package com.novaraspace.model.dto.booking;

import com.novaraspace.model.dto.flight.BookedFlightDTO;

import java.util.List;

public class AccountBookingDTO {
    private String reference;
    private int paxCount;
    private String paxLastName;
    private List<ExtraServiceDTO> extraServices;
    private BookedFlightDTO departureFlight;
    private BookedFlightDTO returnFlight;
    private boolean cancelled;

    public String getReference() {
        return reference;
    }

    public AccountBookingDTO setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public int getPaxCount() {
        return paxCount;
    }

    public AccountBookingDTO setPaxCount(int paxCount) {
        this.paxCount = paxCount;
        return this;
    }

    public String getPaxLastName() {
        return paxLastName;
    }

    public AccountBookingDTO setPaxLastName(String paxLastName) {
        this.paxLastName = paxLastName;
        return this;
    }

    public List<ExtraServiceDTO> getExtraServices() {
        return extraServices;
    }

    public AccountBookingDTO setExtraServices(List<ExtraServiceDTO> extraServices) {
        this.extraServices = extraServices;
        return this;
    }

    public BookedFlightDTO getDepartureFlight() {
        return departureFlight;
    }

    public AccountBookingDTO setDepartureFlight(BookedFlightDTO departureFlight) {
        this.departureFlight = departureFlight;
        return this;
    }

    public BookedFlightDTO getReturnFlight() {
        return returnFlight;
    }

    public AccountBookingDTO setReturnFlight(BookedFlightDTO returnFlight) {
        this.returnFlight = returnFlight;
        return this;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public AccountBookingDTO setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
        return this;
    }
}
