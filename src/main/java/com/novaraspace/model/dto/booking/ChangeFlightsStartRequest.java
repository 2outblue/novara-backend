package com.novaraspace.model.dto.booking;

import com.novaraspace.model.dto.flight.FlightSearchQueryDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ChangeFlightsStartRequest {
    @NotNull
    @Valid
    private FlightSearchQueryDTO flightSearchQuery;
    @NotEmpty
    private String existingDepFlightId;
    private String existingRetFlightId;

    public FlightSearchQueryDTO getFlightSearchQuery() {
        return flightSearchQuery;
    }

    public ChangeFlightsStartRequest setFlightSearchQuery(FlightSearchQueryDTO flightSearchQuery) {
        this.flightSearchQuery = flightSearchQuery;
        return this;
    }

    public String getExistingDepFlightId() {
        return existingDepFlightId;
    }

    public ChangeFlightsStartRequest setExistingDepFlightId(String existingDepFlightId) {
        this.existingDepFlightId = existingDepFlightId;
        return this;
    }

    public String getExistingRetFlightId() {
        return existingRetFlightId;
    }

    public ChangeFlightsStartRequest setExistingRetFlightId(String existingRetFlightId) {
        this.existingRetFlightId = existingRetFlightId;
        return this;
    }
}
