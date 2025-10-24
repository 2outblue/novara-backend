package com.novaraspace.model.entity;

import com.novaraspace.model.embedded.CabinClassData;
import com.novaraspace.model.enums.FlightStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Flight extends BaseEntity {

    @Column(nullable = false)
    private String publicId;
    @Column(nullable = false)
    private String flightNumber;
    @Column(nullable = false)
    private FlightStatus status;

    @Embedded()
    @AttributeOverrides({
            @AttributeOverride(name = "totalSeats", column = @Column(name = "first_total_seats")),
            @AttributeOverride(name = "basePrice", column = @Column(name = "first_base_price"))
    })
    private CabinClassData firstClass;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "totalSeats", column = @Column(name = "middle_total_seats")),
            @AttributeOverride(name = "basePrice", column = @Column(name = "middle_base_price"))
    })
    private CabinClassData middleClass;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "totalSeats", column = @Column(name = "lower_total_seats")),
            @AttributeOverride(name = "basePrice", column = @Column(name = "lower_base_price"))
    })
    private CabinClassData lowerClass;

    @ManyToOne
    @JoinColumn(name = "departure_location", nullable = false)
    private Location departureLocation;
    @ManyToOne
    @JoinColumn(name = "arrival_location", nullable = false)
    private Location arrivalLocation;
    @Column(nullable = false)
    private Instant departureDate;
    @Column(nullable = false)
    private Instant arrivalDate;
    @Column(nullable = false)
    private int orbitsDeparture;
    @Column(nullable = false)
    private int orbitsArrival;


    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false)
    private int totalDurationMinutes;
    @Column(nullable = false)
    private int totalSeatsAvailable;

    public String getPublicId() {
        return publicId;
    }

    public Flight setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Flight setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public Flight setStatus(FlightStatus status) {
        this.status = status;
        return this;
    }

    public CabinClassData getFirstClass() {
        return firstClass;
    }

    public Flight setFirstClass(CabinClassData firstClass) {
        this.firstClass = firstClass;
        return this;
    }

    public CabinClassData getMiddleClass() {
        return middleClass;
    }

    public Flight setMiddleClass(CabinClassData middleClass) {
        this.middleClass = middleClass;
        return this;
    }

    public CabinClassData getLowerClass() {
        return lowerClass;
    }

    public Flight setLowerClass(CabinClassData lowerClass) {
        this.lowerClass = lowerClass;
        return this;
    }

    public Location getDepartureLocation() {
        return departureLocation;
    }

    public Flight setDepartureLocation(Location departureLocation) {
        this.departureLocation = departureLocation;
        return this;
    }

    public Location getArrivalLocation() {
        return arrivalLocation;
    }

    public Flight setArrivalLocation(Location arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
        return this;
    }

    public Instant getDepartureDate() {
        return departureDate;
    }

    public Flight setDepartureDate(Instant departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public Instant getArrivalDate() {
        return arrivalDate;
    }

    public Flight setArrivalDate(Instant arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public int getOrbitsDeparture() {
        return orbitsDeparture;
    }

    public Flight setOrbitsDeparture(int orbitsDeparture) {
        this.orbitsDeparture = orbitsDeparture;
        return this;
    }

    public int getOrbitsArrival() {
        return orbitsArrival;
    }

    public Flight setOrbitsArrival(int orbitsArrival) {
        this.orbitsArrival = orbitsArrival;
        return this;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Flight setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public int getTotalDurationMinutes() {
        return totalDurationMinutes;
    }

    public Flight setTotalDurationMinutes(int totalDurationMinutes) {
        this.totalDurationMinutes = totalDurationMinutes;
        return this;
    }

    public int getTotalSeatsAvailable() {
        return totalSeatsAvailable;
    }

    public Flight setTotalSeatsAvailable(int totalSeatsAvailable) {
        this.totalSeatsAvailable = totalSeatsAvailable;
        return this;
    }
}
