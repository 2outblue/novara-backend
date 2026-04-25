package com.novaraspace.model.enums;

import java.util.Arrays;

public enum DestinationName {
    EARTH, MOON, MARS, VENUS;


    public FlightRegion[] getSupportedRegions() {
        return switch (this) {
            case EARTH -> new FlightRegion[] { FlightRegion.NEAR_EARTH };
            case MOON -> new FlightRegion[] { FlightRegion.MOON, FlightRegion.MOON_ORBIT };
            case MARS -> new FlightRegion[] { FlightRegion.MARS, FlightRegion.MARS_ORBIT };
            case VENUS -> new FlightRegion[] { FlightRegion.VENUS_ORBIT };
        };
    }
}
