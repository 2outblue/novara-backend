package com.novaraspace.model.dto.flight;

import java.time.LocalDate;

public class FlightLimitsDTO {
    private LocalDate departureLowerDate;
    private LocalDate departureUpperDate;
    private LocalDate arrivalLowerDate;
    private LocalDate arrivalUpperDate;

    public LocalDate getDepartureLowerDate() {
        return departureLowerDate;
    }

    public FlightLimitsDTO setDepartureLowerDate(LocalDate departureLowerDate) {
        this.departureLowerDate = departureLowerDate;
        return this;
    }

    public LocalDate getDepartureUpperDate() {
        return departureUpperDate;
    }

    public FlightLimitsDTO setDepartureUpperDate(LocalDate departureUpperDate) {
        this.departureUpperDate = departureUpperDate;
        return this;
    }

    public LocalDate getArrivalLowerDate() {
        return arrivalLowerDate;
    }

    public FlightLimitsDTO setArrivalLowerDate(LocalDate arrivalLowerDate) {
        this.arrivalLowerDate = arrivalLowerDate;
        return this;
    }

    public LocalDate getArrivalUpperDate() {
        return arrivalUpperDate;
    }

    public FlightLimitsDTO setArrivalUpperDate(LocalDate arrivalUpperDate) {
        this.arrivalUpperDate = arrivalUpperDate;
        return this;
    }
}
