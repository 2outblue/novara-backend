package com.novaraspace.model.dto.flight;

import java.time.LocalDate;

public class FlightLimitsDTO {
    private LocalDate departureLowerDate;
    private LocalDate departureUpperDate;
    private LocalDate returnLowerDate;
    private LocalDate returnUpperDate;

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

    public LocalDate getReturnLowerDate() {
        return returnLowerDate;
    }

    public FlightLimitsDTO setReturnLowerDate(LocalDate returnLowerDate) {
        this.returnLowerDate = returnLowerDate;
        return this;
    }

    public LocalDate getReturnUpperDate() {
        return returnUpperDate;
    }

    public FlightLimitsDTO setReturnUpperDate(LocalDate returnUpperDate) {
        this.returnUpperDate = returnUpperDate;
        return this;
    }
}
