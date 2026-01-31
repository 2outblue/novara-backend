package com.novaraspace.model.dto.flight;

public class BookedFlightDTO {
    private String id;
    private String flightNumber;
    private FlightLeg departure;
    private FlightLeg arrival;
    private int durationMinutes;
    private String vehicleType;
    private String selectedClass;
    private double price;

    public String getId() {
        return id;
    }

    public BookedFlightDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public BookedFlightDTO setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public FlightLeg getDeparture() {
        return departure;
    }

    public BookedFlightDTO setDeparture(FlightLeg departure) {
        this.departure = departure;
        return this;
    }

    public FlightLeg getArrival() {
        return arrival;
    }

    public BookedFlightDTO setArrival(FlightLeg arrival) {
        this.arrival = arrival;
        return this;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public BookedFlightDTO setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
        return this;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public BookedFlightDTO setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public String getSelectedClass() {
        return selectedClass;
    }

    public BookedFlightDTO setSelectedClass(String selectedClass) {
        this.selectedClass = selectedClass;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public BookedFlightDTO setPrice(double price) {
        this.price = price;
        return this;
    }
}
