package com.novaraspace.model.dto.flight;

import java.time.LocalDate;

public class RouteAvailabilityRequestDTO {
    private String departureCode;
    private String arrivalCode;

    public String getDepartureCode() {
        return departureCode;
    }

    public RouteAvailabilityRequestDTO setDepartureCode(String departureCode) {
        this.departureCode = departureCode;
        return this;
    }

    public String getArrivalCode() {
        return arrivalCode;
    }

    public RouteAvailabilityRequestDTO setArrivalCode(String arrivalCode) {
        this.arrivalCode = arrivalCode;
        return this;
    }
}
