package com.novaraspace.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ExtraService extends BaseEntity {
    private String code;
    private double price;

//    @ManyToOne
//    @JoinColumn(name = "booking_id")
//    private Booking booking;

    public String getCode() {
        return code;
    }

    public ExtraService setCode(String code) {
        this.code = code;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public ExtraService setPrice(double price) {
        this.price = price;
        return this;
    }

//    public Booking getBooking() {
//        return booking;
//    }
//
//    public ExtraService setBooking(Booking booking) {
//        this.booking = booking;
//        return this;
//    }
}
