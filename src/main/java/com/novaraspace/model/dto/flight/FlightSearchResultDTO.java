package com.novaraspace.model.dto.flight;

import java.util.List;

public class FlightSearchResultDTO {
    private List<AvailableFlightDTO> departureFlights;
    private List<AvailableFlightDTO> returnFlights;
    private FlightLimitsDTO limits;

    public List<AvailableFlightDTO> getDepartureFlights() {
        return departureFlights;
    }

    public FlightSearchResultDTO setDepartureFlights(List<AvailableFlightDTO> departureFlights) {
        this.departureFlights = departureFlights;
        return this;
    }

    public List<AvailableFlightDTO> getReturnFlights() {
        return returnFlights;
    }

    public FlightSearchResultDTO setReturnFlights(List<AvailableFlightDTO> returnFlights) {
        this.returnFlights = returnFlights;
        return this;
    }

    public FlightLimitsDTO getLimits() {
        return limits;
    }

    public FlightSearchResultDTO setLimits(FlightLimitsDTO limits) {
        this.limits = limits;
        return this;
    }

}
