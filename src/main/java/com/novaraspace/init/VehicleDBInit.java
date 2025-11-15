package com.novaraspace.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novaraspace.model.entity.CabinClass;
import com.novaraspace.model.entity.Vehicle;
import com.novaraspace.model.enums.FlightRegion;
import com.novaraspace.model.enums.VehicleAmenity;
import com.novaraspace.model.other.VehicleJSON;
import com.novaraspace.repository.CabinClassRepository;
import com.novaraspace.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Order(2)
public class VehicleDBInit implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final VehicleRepository vehicleRepository;
    private final CabinClassRepository cabinClassRepository;

    public VehicleDBInit(ObjectMapper objectMapper, VehicleRepository vehicleRepository, CabinClassRepository cabinClassRepository) {
        this.objectMapper = objectMapper;
        this.vehicleRepository = vehicleRepository;
        this.cabinClassRepository = cabinClassRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (vehicleRepository.count() == 0) {
            init();
        }
    }

    private void init() throws IOException {
        ClassPathResource vehiclesResource = new ClassPathResource("data/Vehicles.json");
        List<VehicleJSON> vehiclesData = Arrays.asList(objectMapper.readValue(vehiclesResource.getInputStream(), VehicleJSON[].class));

        Map<Long, CabinClass> ccMap = cabinClassRepository.findAll().stream()
                .collect(Collectors.toMap(CabinClass::getId, c -> c));

        List<Vehicle> vehicles = vehiclesData.stream().map(v -> {
            Set<FlightRegion> regions = v.getSupportedRegions().stream().map(FlightRegion::valueOf).collect(Collectors.toSet());
            return new Vehicle()
                    .setCode(v.getCode())
                    .setName(v.getName())
                    .setAmenities(getVehicleAmenities(v))
//                    .setEva(v.isEva())
//                    .setObservationLounge(v.isObservationLounge())
//                    .setVr(v.isVr())
//                    .setGalley(v.isGalley())
                    .setFirstClass(ccMap.get(v.getFirstClassId()))
                    .setMiddleClass(ccMap.get(v.getMiddleClassId()))
                    .setLowerClass(ccMap.get(v.getLowerClassId()))
                    .setSupportedRegions(regions);

        }).toList();

        vehicleRepository.saveAll(vehicles);
    }

    private Set<VehicleAmenity> getVehicleAmenities(VehicleJSON json) {
        return json.getAmenities().stream().map(VehicleAmenity::valueOf)
                .collect(Collectors.toSet());
    }
}
