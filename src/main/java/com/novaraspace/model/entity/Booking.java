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

    @OneToMany(mappedBy = "booking")
    private List<Passenger> passengers;

    @OneToMany(mappedBy = "booking")
    private List<ExtraService> extraServices;

    private String contactCountryCode;
    private String contactMobile;
    private String contactEmail;

    private String billingEmail;
    private String billingMobile;
}
