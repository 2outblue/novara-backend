package com.novaraspace.model.dto.booking;

import com.novaraspace.model.enums.BaggageCapacity;
import jakarta.validation.constraints.AssertTrue;

public class PassengerBaggageDTO {
    private BaggageCapacity capacity;

    private double price;

    @AssertTrue
    public boolean isValid() {
        boolean noBaggage = capacity == null && price == 0;
        boolean baggageIsValid = capacity != null && price > 0;
        return noBaggage || baggageIsValid;
    }

    public BaggageCapacity getCapacity() {
        return capacity;
    }

    public PassengerBaggageDTO setCapacity(BaggageCapacity capacity) {
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
