package com.novaraspace.model.other;

import java.time.LocalTime;

public class FlightJSON {
    private String publicIdPrefix;
    private String flightNumber;

    private double basePrice;

    private LocalTime departureTime;
    private Long departureLocationId;
    private Long arrivalLocationId;
    private int orbitsDeparture;
    private int orbitsArrival;

    private Long vehicleId;
    private int durationMinutes;
    private String weeklySchedule;

    public String getPublicIdPrefix() {
        return publicIdPrefix;
    }

    public FlightJSON setPublicIdPrefix(String publicIdPrefix) {
        this.publicIdPrefix = publicIdPrefix;
        return this;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public FlightJSON setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public FlightJSON setBasePrice(double basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    //    public double getFirstBasePrice() {
//        return firstBasePrice;
//    }
//
//    public FlightJSON setFirstBasePrice(double firstBasePrice) {
//        this.firstBasePrice = firstBasePrice;
//        return this;
//    }
//
//    public double getMiddleBasePrice() {
//        return middleBasePrice;
//    }
//
//    public FlightJSON setMiddleBasePrice(double middleBasePrice) {
//        this.middleBasePrice = middleBasePrice;
//        return this;
//    }
//
//    public double getLowerBasePrice() {
//        return lowerBasePrice;
//    }
//
//    public FlightJSON setLowerBasePrice(double lowerBasePrice) {
//        this.lowerBasePrice = lowerBasePrice;
//        return this;
//    }

    public Long getDepartureLocationId() {
        return departureLocationId;
    }

    public FlightJSON setDepartureLocationId(Long departureLocationId) {
        this.departureLocationId = departureLocationId;
        return this;
    }

    public Long getArrivalLocationId() {
        return arrivalLocationId;
    }

    public FlightJSON setArrivalLocationId(Long arrivalLocationId) {
        this.arrivalLocationId = arrivalLocationId;
        return this;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public FlightJSON setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public FlightJSON setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
        return this;
    }

    public int getOrbitsDeparture() {
        return orbitsDeparture;
    }

    public FlightJSON setOrbitsDeparture(int orbitsDeparture) {
        this.orbitsDeparture = orbitsDeparture;
        return this;
    }

    public int getOrbitsArrival() {
        return orbitsArrival;
    }

    public FlightJSON setOrbitsArrival(int orbitsArrival) {
        this.orbitsArrival = orbitsArrival;
        return this;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public FlightJSON setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public String getWeeklySchedule() {
        return weeklySchedule;
    }

    public FlightJSON setWeeklySchedule(String weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
        return this;
    }
}
