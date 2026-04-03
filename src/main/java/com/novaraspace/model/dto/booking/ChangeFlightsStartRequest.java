package com.novaraspace.model.dto.booking;

import com.novaraspace.model.dto.flight.FlightSearchParamsDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChangeFlightsStartRequest {
    @NotNull
    @Valid
    private FlightSearchParamsDTO flightSearchParams;
    @NotBlank
    @Size(min = 2, max = 50)
    private String existingDepFlightId;
    @Size(min = 2, max = 50)
    private String existingRetFlightId;

    public FlightSearchParamsDTO getFlightSearchParams() {
        return flightSearchParams;
    }

    public ChangeFlightsStartRequest setFlightSearchParams(FlightSearchParamsDTO flightSearchParams) {
        this.flightSearchParams = flightSearchParams;
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
