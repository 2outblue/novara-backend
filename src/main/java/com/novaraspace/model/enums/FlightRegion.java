package com.novaraspace.model.enums;

public enum FlightRegion {
    EARTH("Earth", "E", 0, 0),
    NEAR_EARTH("Near Earth", "EN", 45, 0.9),
    MOON("Moon", "L", 540, 1),
    MOON_ORBIT("Moon Orbit", "LO", 610, 1.2),
    MARS("Mars", "M", 7200, 6),
    MARS_ORBIT("Mars Orbit", "MO", 7330, 6.2),
    VENUS_ORBIT("Venus Orbit", "VO", 4320, 4);

    private final String label;
    private final String regionCode;
    private final int travelMinutesFromEarth;
    private final double separationFactor;

    FlightRegion(String label, String regionCode, int travelMinutesFromEarth, double separationFactor) {
        this.label = label;
        this.regionCode = regionCode;
        this.travelMinutesFromEarth = travelMinutesFromEarth;
        this.separationFactor = separationFactor;
    }

    public String getLabel() {
        return label;
    }
    public String getRegionCode() {return regionCode;}

    public int getTravelMinutesFromEarth() {
        return travelMinutesFromEarth;
    }

    public double getSeparationFactor() {
        return separationFactor;
    }
}
