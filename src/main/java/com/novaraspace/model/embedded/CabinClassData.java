package com.novaraspace.model.embedded;

public class CabinClassData {
    private int totalSeats;
    private double basePrice;

    public int getTotalSeats() {
        return totalSeats;
    }

    public CabinClassData setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
        return this;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public CabinClassData setBasePrice(double basePrice) {
        this.basePrice = basePrice;
        return this;
    }
}
