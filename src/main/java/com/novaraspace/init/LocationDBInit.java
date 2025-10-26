package com.novaraspace.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novaraspace.model.entity.Location;
import com.novaraspace.model.enums.FlightLocation;
import com.novaraspace.model.other.LocationJSON;
import com.novaraspace.repository.LocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Order(1)
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
            LocationJSON matchingLocation = locationsMap.get(fl);
            Location location = new Location()
                    .setRegion(fl.getRegion())
                    .setLocation(fl)
                    .setName(matchingLocation.getName())
                    .setLongName(matchingLocation.getLongName())
                    .setCode(matchingLocation.getCode());
            locationsToSave.add(location);
        }
        locationRepository.saveAll(locationsToSave);
    }
}
