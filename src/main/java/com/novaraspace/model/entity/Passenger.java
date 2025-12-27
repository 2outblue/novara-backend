package com.novaraspace.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;

@Entity
public class Passenger extends BaseEntity {
    private long intraBookingId;
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String ageGroup;
    private long cabinId;

    @ManyToOne
    private Booking booking;
    @OneToOne
    private PassengerBaggage baggage;


}
