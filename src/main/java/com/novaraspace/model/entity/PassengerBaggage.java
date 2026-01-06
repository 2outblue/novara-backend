package com.novaraspace.model.entity;

import jakarta.persistence.Entity;

//@Entity
public class PassengerBaggage {
    private String capacity;
    private double price;

    public String getCapacity() {
        return capacity;
    }

    public PassengerBaggage setCapacity(String capacity) {
        this.capacity = capacity;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public PassengerBaggage setPrice(double price) {
        this.price = price;
        return this;
    }
}
