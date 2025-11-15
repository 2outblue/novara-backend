package com.novaraspace.model.dto.flight;

import com.novaraspace.model.entity.FlightInstance;

import java.util.List;

public class FlightSearchResultDTO {
    private List<FlightUiDTO> departureFlights;
    private List<FlightUiDTO> returnFlights;

    public List<FlightUiDTO> getDepartureFlights() {
        return departureFlights;
    }

    public FlightSearchResultDTO setDepartureFlights(List<FlightUiDTO> departureFlights) {
        this.departureFlights = departureFlights;
        return this;
    }

    public List<FlightUiDTO> getReturnFlights() {
        return returnFlights;
    }

    public FlightSearchResultDTO setReturnFlights(List<FlightUiDTO> returnFlights) {
        this.returnFlights = returnFlights;
        return this;
    }
}
