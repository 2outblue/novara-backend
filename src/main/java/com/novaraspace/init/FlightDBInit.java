package com.novaraspace.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novaraspace.model.dto.flight.FlightInstanceGenerationParams;
import com.novaraspace.model.entity.*;
import com.novaraspace.model.other.FlightJSON;
import com.novaraspace.repository.*;
import com.novaraspace.service.FlightGenerationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Order(3)
public class FlightDBInit implements CommandLineRunner {
    private final ObjectMapper mapper;
    private final LocationRepository locationRepository;
    private final VehicleRepository vehicleRepository;
    private final FlightGenerationService flightGenerationService;
    private final FlightTemplateRepository flightTemplateRepository;

    private Map<Long, Location> locationsMap;
    private Map<Long, Vehicle> vehiclesMap;

    public FlightDBInit(ObjectMapper mapper, LocationRepository locationRepository, VehicleRepository vehicleRepository, FlightGenerationService flightGenerationService, FlightTemplateRepository flightTemplateRepository) {
        this.mapper = mapper;
        this.locationRepository = locationRepository;
        this.vehicleRepository = vehicleRepository;
        this.flightGenerationService = flightGenerationService;
        this.flightTemplateRepository = flightTemplateRepository;


    }

    @Override
    public void run(String... args) throws Exception {
        if (flightTemplateRepository.count() == 0) {
            locationsMap = this.locationRepository.findAll().stream()
                    .collect(Collectors.toMap(Location::getId, l -> l));
            vehiclesMap = this.vehicleRepository.findAll().stream()
                    .collect(Collectors.toMap(Vehicle::getId, v -> v));

            generateFlights();
        }
    }

    private void generateFlights() throws IOException {
        ClassPathResource flightsResource = new ClassPathResource("data/Flights.json");
        List<FlightJSON> flightsData = Arrays.asList(mapper.readValue(flightsResource.getInputStream(), FlightJSON[].class));

        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusMonths(1);

        List<FlightTemplate> templatesWithInstances1 = flightsData.stream()
                .map(this::jsonToTemplate)
                .map(t -> {
                    List<FlightInstance> instances = flightGenerationService
                            .generateInstancesForTemplate(t, new FlightInstanceGenerationParams(startDate, endDate));
                    return t.setInstances(instances);
                }).toList();
        flightTemplateRepository.saveAll(templatesWithInstances1);

    }

    private FlightTemplate jsonToTemplate(FlightJSON json) {
        Location departureLocation = locationsMap.get(json.getDepartureLocationId());
        Location arrivalLocation = locationsMap.get(json.getArrivalLocationId());
        Vehicle vehicle = vehiclesMap.get(json.getVehicleId());

        if (departureLocation == null || arrivalLocation == null || vehicle == null) {
            throw new RuntimeException("Could not convert Flight data to FlightTemplate");
        }

        return new FlightTemplate()
                .setPublicIdPrefix(json.getPublicIdPrefix())
                .setFlightNumber(json.getFlightNumber())
                .setBasePrice(json.getBasePrice())
                .setDepartureTime(json.getDepartureTime())
                .setDepartureLocation(departureLocation)
                .setArrivalLocation(arrivalLocation)
                .setOrbitsDeparture(json.getOrbitsDeparture())
                .setOrbitsArrival(json.getOrbitsArrival())
                .setVehicle(vehicle)
                .setDurationMinutes(json.getDurationMinutes())
                .setWeeklySchedule(json.getWeeklySchedule())
                .setInstances(new ArrayList<>());
    }
}
