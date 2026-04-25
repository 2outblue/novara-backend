package com.novaraspace.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novaraspace.model.embedded.DestinationPoint;
import com.novaraspace.model.entity.Destination;
import com.novaraspace.model.entity.Location;
import com.novaraspace.model.enums.FlightRegion;
import com.novaraspace.model.other.LocationJSON;
import com.novaraspace.repository.DestinationRepository;
import com.novaraspace.repository.LocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Order(5)
public class DestinationDBInit implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final DestinationRepository destinationRepository;
    private final LocationRepository locationRepository;

    public DestinationDBInit(ObjectMapper objectMapper, DestinationRepository destinationRepository, LocationRepository locationRepository) {
        this.objectMapper = objectMapper;
        this.destinationRepository = destinationRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (destinationRepository.count() == 0) {
            initDestinations();
        }
    }


    public void initDestinations() throws IOException {
        ClassPathResource destinationsPath = new ClassPathResource("data/Destinations.json");
        List<Destination> rawDestinations = Arrays.asList(objectMapper.readValue(destinationsPath.getInputStream(), Destination[].class));

        List<Destination> fullDestinations = new ArrayList<>();
        for (int i = 0; i < rawDestinations.size(); i++) {
            Destination destination = rawDestinations.get(i);
            List<Location> destLocations = getLocations(destination);
            destination.setLocations(destLocations);
            fullDestinations.add(destination);
        }
        destinationRepository.saveAll(fullDestinations);
    }

    private List<Location> getLocations(Destination destination) {
        FlightRegion[] supportedRegions = destination.getName().getSupportedRegions();

        List<Location> allLocations = new ArrayList<>();
        for (FlightRegion region : supportedRegions) {
            List<Location> locations = locationRepository.findAllByRegion(region);
            allLocations.addAll(locations);
        }
        return allLocations;
    }

}
