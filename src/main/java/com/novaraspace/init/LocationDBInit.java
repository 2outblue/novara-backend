package com.novaraspace.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novaraspace.model.entity.Location;
import com.novaraspace.model.enums.FlightLocation;
import com.novaraspace.model.other.LocationJSON;
import com.novaraspace.repository.LocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class LocationDBInit implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final LocationRepository locationRepository;

    public LocationDBInit(ObjectMapper objectMapper, LocationRepository locationRepository) {
        this.objectMapper = objectMapper;
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (locationRepository.count() == 0) {
            initLocations();
        }
    }

    public void initLocations() throws IOException {
        ClassPathResource locationsPath = new ClassPathResource("data/Locations.json");
        List<LocationJSON> locationsData = Arrays.asList(objectMapper.readValue(locationsPath.getInputStream(), LocationJSON[].class));
        Map<FlightLocation, LocationJSON> locationsMap = locationsData.stream()
                .collect(Collectors.toMap(l -> FlightLocation.valueOf(l.getLocation()), l -> l ));

        List<Location> locationsToSave = new ArrayList<>();
        for (FlightLocation fl : FlightLocation.values()) {
            Location location = new Location()
                    .setRegion(fl.getRegion())
                    .setLocation(fl)
                    .setName(locationsMap.get(fl).getName());
            locationsToSave.add(location);
        }
        locationRepository.saveAll(locationsToSave);
    }
}
