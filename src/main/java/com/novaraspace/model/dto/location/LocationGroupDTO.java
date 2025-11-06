package com.novaraspace.model.dto.location;

import com.novaraspace.model.enums.FlightRegion;

import java.util.List;

public class LocationGroupDTO {
    private String regionLabel;
    private FlightRegion regionCode;
    private double regionSeparationFactor;
    private List<LocationDTO> locations;

    public String getRegionLabel() {
        return regionLabel;
    }

    public LocationGroupDTO setRegionLabel(String regionLabel) {
        this.regionLabel = regionLabel;
        return this;
    }

    public FlightRegion getRegionCode() {
        return regionCode;
    }

    public LocationGroupDTO setRegionCode(FlightRegion regionCode) {
        this.regionCode = regionCode;
        return this;
    }

    public double getRegionSeparationFactor() {
        return regionSeparationFactor;
    }

    public LocationGroupDTO setRegionSeparationFactor(double regionSeparationFactor) {
        this.regionSeparationFactor = regionSeparationFactor;
        return this;
    }

    public List<LocationDTO> getLocations() {
        return locations;
    }

    public LocationGroupDTO setLocations(List<LocationDTO> locations) {
        this.locations = locations;
        return this;
    }
}
