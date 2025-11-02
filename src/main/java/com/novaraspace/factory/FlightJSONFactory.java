package com.novaraspace.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novaraspace.model.dto.flight.FlightTemplateGenerationRequest;
import com.novaraspace.model.entity.Location;
import com.novaraspace.model.enums.FlightRegion;
import com.novaraspace.model.other.FlightGenerationProperties;
import com.novaraspace.model.other.FlightJSON;
import com.novaraspace.repository.LocationRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FlightJSONFactory {

    private final LocationRepository locationRepository;
    private final ObjectMapper mapper;
    private FlightGenerationProperties flightProps;

    public FlightJSONFactory(LocationRepository locationRepository, ObjectMapper objectMapper) {
        this.locationRepository = locationRepository;
        this.mapper = objectMapper;
        initFlightProps();
    }

    private void initFlightProps() {
        ClassPathResource propsResource = new ClassPathResource("data/FlightGenerationProperties.json");
        try {
            this.flightProps = mapper.readValue(propsResource.getInputStream(), FlightGenerationProperties.class);
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: Make exceptions for this domain and throw here correctly
        }
    }

    public FlightJSON generateNewFlightJSON(FlightTemplateGenerationRequest data) {

        Location departureLocation = locationRepository.findById(data.getDepartureLocationId()).orElseThrow();
        Location arrivalLocation = locationRepository.findById(data.getArrivalLocationId()).orElseThrow();
        int sequenceNumber = data.getSequenceNumber();

        String publicId = generateFlightPublicId(departureLocation, arrivalLocation, sequenceNumber);
        String flightNumber = generateFlightNumber(departureLocation, arrivalLocation, sequenceNumber);
        int duration = calculateDurationMinutes(departureLocation, arrivalLocation, data.getOrbitsDeparture(), data.getOrbitsArrival());

        return new FlightJSON()
                .setPublicIdPrefix(publicId)
                .setFlightNumber(flightNumber)
                .setBasePrice(data.getBasePrice())
                .setDepartureLocationId(data.getDepartureLocationId())
                .setArrivalLocationId(data.getArrivalLocationId())
                .setDepartureTime(null)
                .setDurationMinutes(duration)
                .setOrbitsDeparture(data.getOrbitsDeparture())
                .setOrbitsArrival(data.getOrbitsArrival())
                .setVehicleId(data.getVehicleId())
                .setWeeklySchedule("1111111");
    }


    private String generateFlightNumber(Location departureLocation, Location arrivalLocation, int sequenceNumber) {
        String depLocationNumber = departureLocation.getLocationNumber();
        String arrLocationNumber = arrivalLocation.getLocationNumber();
        String company = "NVR";

        return String.format("%s %s-%s%d", company, depLocationNumber, arrLocationNumber, sequenceNumber);
    }

    private String generateFlightPublicId(Location departureLocation, Location arrivalLocation, int sequenceNumber) {
        String depRegionCode = departureLocation.getRegion().getRegionCode();
        String arrRegionCode = arrivalLocation.getRegion().getRegionCode();
        long departureId = departureLocation.getId();
        long arrivalId = arrivalLocation.getId();

        return String.format("%s%d%s%d-%d", depRegionCode, departureId, arrRegionCode, arrivalId, sequenceNumber);
    }

    private int calculateDurationMinutes(Location departureLocation, Location arrivalLocation, int depOrbits, int arrOrbits) {
        Location distanceLocation = departureLocation.getRegion().getTravelMinutesFromEarth() == 0 ? arrivalLocation : departureLocation;

        int rawTravelTime = distanceLocation.getRegion().getTravelMinutesFromEarth();
        int additionalOrbitTIme =
                calculateAdditionalOrbitTime(departureLocation.getRegion(), depOrbits)
                + calculateAdditionalOrbitTime(arrivalLocation.getRegion(), arrOrbits);

        return rawTravelTime + additionalOrbitTIme;
    }

    private int calculateAdditionalOrbitTime(FlightRegion region, int orbits) {
        int additionalTravelMinutes;
        switch (region) {
            case EARTH -> additionalTravelMinutes = flightProps.getEarthOrbitalPeriod();
            case MOON -> additionalTravelMinutes = flightProps.getMoonOrbitalPeriod();
            case MARS -> additionalTravelMinutes = flightProps.getMarsOrbitalPeriod();
            default -> additionalTravelMinutes = 0;
        }
        return additionalTravelMinutes * orbits;
    }


}
