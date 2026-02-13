package com.novaraspace.model.dto.flight;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FlightReserveDTO {
    @NotEmpty
    private String id;
    @NotEmpty
    private String flightClass;
    @NotNull
    @Positive
    private Double price;

    public String getId() {
        return id;
    }

    public FlightReserveDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public FlightReserveDTO setFlightClass(String flightClass) {
        this.flightClass = flightClass;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public FlightReserveDTO setPrice(Double price) {
        this.price = price;
        return this;
    }
}
