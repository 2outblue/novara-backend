package com.novaraspace.model.dto.flight;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class RouteAvailabilityRequestDTO {
    @NotBlank
    @Size(min = 3, max = 4)
    private String departureCode;
    @NotBlank
    @Size(min = 3, max = 4)
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
