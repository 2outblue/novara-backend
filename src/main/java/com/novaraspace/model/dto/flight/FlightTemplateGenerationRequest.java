package com.novaraspace.model.dto.flight;

public class FlightTemplateGenerationRequest {
    private double basePrice;
    private long departureLocationId;
    private long arrivalLocationId;
    private int orbitsDeparture;
    private int orbitsArrival;
    private long vehicleId;
    private int sequenceNumber;

    public double getBasePrice() {
        return basePrice;
    }

    public FlightTemplateGenerationRequest setBasePrice(double basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public long getDepartureLocationId() {
        return departureLocationId;
    }

    public FlightTemplateGenerationRequest setDepartureLocationId(long departureLocationId) {
        this.departureLocationId = departureLocationId;
        return this;
    }

    public long getArrivalLocationId() {
        return arrivalLocationId;
    }

    public FlightTemplateGenerationRequest setArrivalLocationId(long arrivalLocationId) {
        this.arrivalLocationId = arrivalLocationId;
        return this;
    }

    public int getOrbitsDeparture() {
        return orbitsDeparture;
    }

    public FlightTemplateGenerationRequest setOrbitsDeparture(int orbitsDeparture) {
        this.orbitsDeparture = orbitsDeparture;
        return this;
    }

    public int getOrbitsArrival() {
        return orbitsArrival;
    }

    public FlightTemplateGenerationRequest setOrbitsArrival(int orbitsArrival) {
        this.orbitsArrival = orbitsArrival;
        return this;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public FlightTemplateGenerationRequest setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public FlightTemplateGenerationRequest setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }
}
