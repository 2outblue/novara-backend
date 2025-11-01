package com.novaraspace.model.dto.location;

import com.novaraspace.model.enums.FlightLocation;
import com.novaraspace.model.enums.FlightRegion;

public class LocationDTO {
    private String name;
    private String longName;
    private String code;
    private FlightRegion region;

    public String getName() {
        return name;
    }

    public LocationDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getLongName() {
        return longName;
    }

    public LocationDTO setLongName(String longName) {
        this.longName = longName;
        return this;
    }

    public String getCode() {
        return code;
    }

    public LocationDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public FlightRegion getRegion() {
        return region;
    }

    public LocationDTO setRegion(FlightRegion region) {
        this.region = region;
        return this;
    }
}
