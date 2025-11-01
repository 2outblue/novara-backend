package com.novaraspace.model.embedded;

public class CabinClassData {
    private int availableSeats;
    private int lockedSeats;
    private double basePrice;

    public int getAvailableSeats() {
        return availableSeats;
    }

    public CabinClassData setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
        return this;
    }

    public int getLockedSeats() {
        return lockedSeats;
    }

    public CabinClassData setLockedSeats(int lockedSeats) {
        this.lockedSeats = lockedSeats;
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
