package com.novaraspace.model.dto.booking;

import com.novaraspace.model.enums.BaggageCapacity;
import com.novaraspace.validation.annotations.ValidBaggageCapacity;
import com.novaraspace.validation.annotations.ValidNewPassengerBaggage;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

//@ValidNewPassengerBaggage
public class PassengerBaggageDTO {
//    @Size(max = 10)
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
