package com.novaraspace.model.dto.booking;

import com.novaraspace.validation.annotations.ValidBaggageCapacity;
import com.novaraspace.validation.annotations.ValidNewPassengerBaggage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@ValidNewPassengerBaggage
public class PassengerBaggageDTO {
    @Size(max = 10)
    private String capacity;

    private double price;

    public String getCapacity() {
        return capacity;
    }

    public PassengerBaggageDTO setCapacity(String capacity) {
        this.capacity = capacity;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public PassengerBaggageDTO setPrice(double price) {
        this.price = price;
        return this;
    }
}
