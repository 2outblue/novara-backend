package com.novaraspace.model.enums;

public enum FlightLocation {
//    EARTH
        KENNEDY(FlightRegion.EARTH),
        HEATHROW(FlightRegion.EARTH),
        NARITA(FlightRegion.EARTH),
        BEIJING_CAPITAL(FlightRegion.EARTH),
        DUBAI_INTL(FlightRegion.EARTH),
        SAO_PAULO_GUARULHOS(FlightRegion.EARTH),
        MUMBAI_CHHATRAPATI(FlightRegion.EARTH),
        SCHIPHOL(FlightRegion.EARTH),
        CHARLES_DE_GAULLE(FlightRegion.EARTH),
        FRANKFURT(FlightRegion.EARTH),
        INCHEON(FlightRegion.EARTH),
        SYDNEY_KINGSFORD(FlightRegion.EARTH),
        TORONTO_PEARSON(FlightRegion.EARTH),
        SINGAPORE_CHANGI(FlightRegion.EARTH),
        CAPE_TOWN(FlightRegion.EARTH),
//    NEAR_EARTH
        ORBITAL_ALPHA(FlightRegion.NEAR_EARTH),
        GATEWAY_OTP(FlightRegion.NEAR_EARTH),
        NOVARA_CMC(FlightRegion.NEAR_EARTH),
        PANGU_ORIENTAL_CMC(FlightRegion.NEAR_EARTH),
        WAYFARE_INTERPLAN_CMC(FlightRegion.NEAR_EARTH),
        POLAR_RELAY(FlightRegion.NEAR_EARTH),
//    MOON
        SOUTH_POLE(FlightRegion.MOON),
        SHACKLETON_CRATER(FlightRegion.MOON),
        TRANQUILITY_LANDING_SITE(FlightRegion.MOON),
        OCEANUS_PROCELLARUM(FlightRegion.MOON),
        ARISTARCHUS_PLATEAU(FlightRegion.MOON),
//    MOON_ORBIT
        LUNAR_ORBIT_RELAY(FlightRegion.MOON_ORBIT),
        CISLUNAR_HABITAT(FlightRegion.MOON_ORBIT),
        YAMAGUCHI_CMC(FlightRegion.MOON_ORBIT),
        AL_MANSOUR_CMC(FlightRegion.MOON_ORBIT),
//    MARS
        OLYMPUS_MONS(FlightRegion.MARS),
        VALLES_MARINERIS_OTP(FlightRegion.MARS),
        ACIDALIA_PLANITIA(FlightRegion.MARS),
//    MARS_ORBIT
        AREOSYNCHRONOUS_STATION_ALPHA(FlightRegion.MARS_ORBIT),
        PHOBOS_RELAY(FlightRegion.MARS_ORBIT),
        DEIMOS_HABITAT(FlightRegion.MARS_ORBIT),
        RED_PLANET(FlightRegion.MARS_ORBIT),
        ORBIT_LAB_OBS(FlightRegion.MARS_ORBIT),
//    VENUS_ORBIT
        CYTHEREAN_HABITAT(FlightRegion.VENUS_ORBIT),
        CLOUD_DOCK_OBS(FlightRegion.VENUS_ORBIT);

    private final FlightRegion region;

    FlightLocation(FlightRegion region) {
        this.region = region;
    }

    public FlightRegion getRegion() {
        return region;
    }
}
