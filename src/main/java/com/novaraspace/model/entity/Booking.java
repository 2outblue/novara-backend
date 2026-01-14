package com.novaraspace.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Booking extends BaseEntity {
    @Column(unique = true)
    private String reference;
    @ManyToOne
    private FlightInstance departureFlight;
    @ManyToOne
    private FlightInstance returnFlight;

    @OneToMany
    private List<Passenger> passengers;

    @OneToMany
    private List<ExtraService> extraServices;

    private String contactCountryCode;
    private String contactMobile;
    private String contactEmail;

    private String billingEmail;
    private String billingMobile;

    private LocalDateTime createdAt;
    private boolean cancelled = false;

    public String getReference() {
        return reference;
    }

    public Booking setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public FlightInstance getDepartureFlight() {
        return departureFlight;
    }

    public Booking setDepartureFlight(FlightInstance departureFlight) {
        this.departureFlight = departureFlight;
        return this;
    }

    public FlightInstance getReturnFlight() {
        return returnFlight;
    }

    public Booking setReturnFlight(FlightInstance returnFlight) {
        this.returnFlight = returnFlight;
        return this;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public Booking setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
        return this;
    }

    public List<ExtraService> getExtraServices() {
        return extraServices;
    }

    public Booking setExtraServices(List<ExtraService> extraServices) {
        this.extraServices = extraServices;
        return this;
    }

    public String getContactCountryCode() {
        return contactCountryCode;
    }

    public Booking setContactCountryCode(String contactCountryCode) {
        this.contactCountryCode = contactCountryCode;
        return this;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public Booking setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
        return this;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public Booking setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public Booking setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
        return this;
    }

    public String getBillingMobile() {
        return billingMobile;
    }

    public Booking setBillingMobile(String billingMobile) {
        this.billingMobile = billingMobile;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Booking setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Booking setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
        return this;
    }
}
