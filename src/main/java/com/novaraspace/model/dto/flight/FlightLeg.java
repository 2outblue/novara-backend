package com.novaraspace.model.dto.flight;

import com.novaraspace.model.enums.FlightRegion;

import java.time.LocalDateTime;

public class FlightLeg {
    private FlightRegion region;
    private String location;
    private String locationCode;
    private LocalDateTime date;
    private int minimumOrbits;

    public FlightRegion getRegion() {
        return region;
    }

    public FlightLeg setRegion(FlightRegion region) {
        this.region = region;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public FlightLeg setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public FlightLeg setLocationCode(String locationCode) {
        this.locationCode = locationCode;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public FlightLeg setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public int getMinimumOrbits() {
        return minimumOrbits;
    }

    public FlightLeg setMinimumOrbits(int minimumOrbits) {
        this.minimumOrbits = minimumOrbits;
        return this;
    }
}
