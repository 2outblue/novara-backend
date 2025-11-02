package com.novaraspace.model.entity;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;

@Entity
public class FlightTemplate extends BaseEntity {
    @Column(nullable = false)
    private String publicIdPrefix;
    @Column(nullable = false)
    private String flightNumber;

    @Column(nullable = false)
    private double basePrice;

    @Column(nullable = false)
    private LocalTime departureTime;
    @ManyToOne
    @JoinColumn(name = "departure_location", nullable = false)
    private Location departureLocation;
    @ManyToOne
    @JoinColumn(name = "arrival_location", nullable = false)
    private Location arrivalLocation;

    @Column(nullable = false)
    private int orbitsDeparture;
    @Column(nullable = false)
    private int orbitsArrival;
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    @Column(nullable = false)
    private int durationMinutes;
    @Column(nullable = false, length = 7)
    private String weeklySchedule;

    @OneToMany(mappedBy = "flightTemplate", cascade = CascadeType.ALL)
    private List<FlightInstance> instances;

    public String getPublicIdPrefix() {
        return publicIdPrefix;
    }

    public FlightTemplate setPublicIdPrefix(String publicIdPrefix) {
        this.publicIdPrefix = publicIdPrefix;
        return this;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public FlightTemplate setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public FlightTemplate setBasePrice(double basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    //    public double getFirstBasePrice() {
//        return firstBasePrice;
//    }
//
//    public FlightTemplate setFirstBasePrice(double firstBasePrice) {
//        this.firstBasePrice = firstBasePrice;
//        return this;
//    }
//
//    public double getMiddleBasePrice() {
//        return middleBasePrice;
//    }
//
//    public FlightTemplate setMiddleBasePrice(double middleBasePrice) {
//        this.middleBasePrice = middleBasePrice;
//        return this;
//    }
//
//    public double getLowerBasePrice() {
//        return lowerBasePrice;
//    }
//
//    public FlightTemplate setLowerBasePrice(double lowerBasePrice) {
//        this.lowerBasePrice = lowerBasePrice;
//        return this;
//    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public FlightTemplate setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public Location getDepartureLocation() {
        return departureLocation;
    }

    public FlightTemplate setDepartureLocation(Location departureLocation) {
        this.departureLocation = departureLocation;
        return this;
    }

    public Location getArrivalLocation() {
        return arrivalLocation;
    }

    public FlightTemplate setArrivalLocation(Location arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
        return this;
    }

    public int getOrbitsDeparture() {
        return orbitsDeparture;
    }

    public FlightTemplate setOrbitsDeparture(int orbitsDeparture) {
        this.orbitsDeparture = orbitsDeparture;
        return this;
    }

    public int getOrbitsArrival() {
        return orbitsArrival;
    }

    public FlightTemplate setOrbitsArrival(int orbitsArrival) {
        this.orbitsArrival = orbitsArrival;
        return this;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public FlightTemplate setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public FlightTemplate setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
        return this;
    }

    public String getWeeklySchedule() {
        return weeklySchedule;
    }

    public FlightTemplate setWeeklySchedule(String weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
        return this;
    }

    public List<FlightInstance> getInstances() {
        return instances;
    }

    public FlightTemplate setInstances(List<FlightInstance> instances) {
        this.instances = instances;
        return this;
    }
}
