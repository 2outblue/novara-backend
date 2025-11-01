package com.novaraspace.model.enums;

public enum FlightRegion {
    EARTH("Earth", "E", 0),
    NEAR_EARTH("Near Earth", "EN", 45),
    MOON("Moon", "L", 540),
    MOON_ORBIT("Moon Orbit", "LO", 610),
    MARS("Mars", "M", 7200),
    MARS_ORBIT("Mars Orbit", "MO", 7330),
    VENUS_ORBIT("Venus Orbit", "VO", 4320);

    private final String label;
    private final String regionCode;
    private final int travelMinutesFromEarth;

    FlightRegion(String label, String regionCode, int travelMinutesFromEarth) {
        this.label = label;
        this.regionCode = regionCode;
        this.travelMinutesFromEarth = travelMinutesFromEarth;
    }

    public String getLabel() {
        return label;
    }
    public String getRegionCode() {return regionCode;}

    public int getTravelMinutesFromEarth() {
        return travelMinutesFromEarth;
    }
}
