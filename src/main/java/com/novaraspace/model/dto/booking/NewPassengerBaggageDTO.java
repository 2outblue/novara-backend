package com.novaraspace.model.dto.booking;

import com.novaraspace.validation.annotations.ValidBaggageCapacity;
import com.novaraspace.validation.annotations.ValidNewPassengerBaggage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

@ValidNewPassengerBaggage
public class NewPassengerBaggageDTO {
//    @NotEmpty
//    @ValidBaggageCapacity
    private String capacity;
//    @Positive
    private double price;

    public String getCapacity() {
        return capacity;
    }

    public NewPassengerBaggageDTO setCapacity(String capacity) {
        this.capacity = capacity;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public NewPassengerBaggageDTO setPrice(double price) {
        this.price = price;
        return this;
    }
}
