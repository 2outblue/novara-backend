package com.novaraspace.model.domain;

import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.enums.CabinClassEnum;

public record FlightReserveContext(
        FlightInstance flight,
        CabinClassEnum cabinClass,
        int paxCount
) {

}
