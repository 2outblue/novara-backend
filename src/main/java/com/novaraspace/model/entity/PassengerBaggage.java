package com.novaraspace.model.entity;

import com.novaraspace.model.enums.BaggageCapacity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

public class PassengerBaggage {
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private BaggageCapacity capacity;
    private double price;

    public BaggageCapacity getCapacity() {
        return capacity;
    }

    public PassengerBaggage setCapacity(BaggageCapacity capacity) {
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
