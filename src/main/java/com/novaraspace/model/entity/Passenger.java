package com.novaraspace.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

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
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Embedded()
    @AttributeOverrides({
            @AttributeOverride(name = "capacity", column = @Column(name = "extra_baggage_capacity")),
            @AttributeOverride(name = "price", column = @Column(name = "extra_baggage_price"))
    })
    private PassengerBaggage baggage;

    public long getIntraBookingId() {
        return intraBookingId;
    }

    public Passenger setIntraBookingId(long intraBookingId) {
        this.intraBookingId = intraBookingId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Passenger setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Passenger setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Passenger setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Passenger setDob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public Passenger setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
        return this;
    }

    public long getCabinId() {
        return cabinId;
    }

    public Passenger setCabinId(long cabinId) {
        this.cabinId = cabinId;
        return this;
    }

    public Booking getBooking() {
        return booking;
    }

    public Passenger setBooking(Booking booking) {
        this.booking = booking;
        return this;
    }

    public PassengerBaggage getBaggage() {
        return baggage;
    }

    public Passenger setBaggage(PassengerBaggage baggage) {
        this.baggage = baggage;
        return this;
    }
}
