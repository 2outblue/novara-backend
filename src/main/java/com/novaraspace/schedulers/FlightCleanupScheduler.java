package com.novaraspace.schedulers;

import com.novaraspace.repository.FlightInstanceRepository;
import org.springframework.stereotype.Component;

@Component
public class FlightCleanupScheduler {

    private final FlightInstanceRepository flightInstanceRepository;

    public FlightCleanupScheduler(FlightInstanceRepository flightInstanceRepository) {
        this.flightInstanceRepository = flightInstanceRepository;
    }


}
