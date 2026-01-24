package com.novaraspace.model.entity;

import com.novaraspace.model.enums.CabinClassEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Booking extends BaseEntity {
    @Column(unique = true)
    private String reference;
    @ManyToOne
    private FlightInstance departureFlight;
    private CabinClassEnum departureClass;
    @ManyToOne
    private FlightInstance returnFlight;
    private CabinClassEnum returnClass;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Passenger> passengers = new ArrayList<>();

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<ExtraService> extraServices = new ArrayList<>();

    private String contactCountryCode;
    private String contactMobile;
    private String contactEmail;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

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

    public CabinClassEnum getDepartureClass() {
        return departureClass;
    }

    public Booking setDepartureClass(CabinClassEnum departureClass) {
        this.departureClass = departureClass;
        return this;
    }

    public FlightInstance getReturnFlight() {
        return returnFlight;
    }

    public Booking setReturnFlight(FlightInstance returnFlight) {
        this.returnFlight = returnFlight;
        return this;
    }

    public CabinClassEnum getReturnClass() {
        return returnClass;
    }

    public Booking setReturnClass(CabinClassEnum returnClass) {
        this.returnClass = returnClass;
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

    public Payment getPayment() {
        return payment;
    }

    public Booking setPayment(Payment payment) {
        this.payment = payment;
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
