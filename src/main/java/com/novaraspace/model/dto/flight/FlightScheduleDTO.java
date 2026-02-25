package com.novaraspace.model.dto.flight;

import java.time.LocalDateTime;

public class FlightScheduleDTO {
    private String vehicleName;
    private String flightNumber;
    private String regularity;
    private LocalDateTime departDate;
    private LocalDateTime arrivalDate;
    private String departCode;
    private String arriveCode;

    public String getVehicleName() {
        return vehicleName;
    }

    public FlightScheduleDTO setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
        return this;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public FlightScheduleDTO setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public String getRegularity() {
        return regularity;
    }

    public FlightScheduleDTO setRegularity(String regularity) {
        this.regularity = regularity;
        return this;
    }

    public LocalDateTime getDepartDate() {
        return departDate;
    }

    public FlightScheduleDTO setDepartDate(LocalDateTime departDate) {
        this.departDate = departDate;
        return this;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public FlightScheduleDTO setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public String getDepartCode() {
        return departCode;
    }

    public FlightScheduleDTO setDepartCode(String departCode) {
        this.departCode = departCode;
        return this;
    }

    public String getArriveCode() {
        return arriveCode;
    }

    public FlightScheduleDTO setArriveCode(String arriveCode) {
        this.arriveCode = arriveCode;
        return this;
    }
}
