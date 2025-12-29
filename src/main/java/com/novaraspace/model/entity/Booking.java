package com.novaraspace.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Booking extends BaseEntity {
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
}
