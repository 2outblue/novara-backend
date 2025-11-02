package com.novaraspace.init;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@Component
@Order(3)
public class FlightDBInit implements CommandLineRunner {

    private final ObjectMapper mapper;
    private final LocationRepository locationRepository;
    private final VehicleRepository vehicleRepository;
    private final FlightGenerationService flightGenerationService;
    private final FlightTemplateRepository flightTemplateRepository;


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
                    List<FlightInstance> instances = flightGenerationService.generateInstancesForTemplate(t, startDate, endDate);
                    return t.setInstances(instances);
                }).toList();
        flightTemplateRepository.saveAll(templatesWithInstances1);

    }

    private FlightTemplate jsonToTemplate(FlightJSON json) {
        Location departureLocation = locationRepository.findById(json.getDepartureLocationId()).orElseThrow();
        Location arrivalLocation = locationRepository.findById(json.getArrivalLocationId()).orElseThrow();
        Vehicle vehicle = vehicleRepository.findById(json.getVehicleId()).orElseThrow();

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
