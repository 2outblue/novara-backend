package com.novaraspace.model.mapper;

import com.novaraspace.model.dto.flight.FlightUiDTO;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.entity.FlightTemplate;
import com.novaraspace.model.entity.Vehicle;
import com.novaraspace.model.enums.VehicleAmenity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class FlightMapper {
    public FlightUiDTO instanceToFlightUiDTO(FlightInstance instance) {
        FlightTemplate template = instance.getFlightTemplate();
        Vehicle vehicle = template.getVehicle();

        FlightUiDTO.FlightLeg departure = new FlightUiDTO.FlightLeg()
                .setRegion(template.getDepartureLocation().getRegion())
                .setLocation(template.getDepartureLocation().getName())
                .setDate(instance.getDepartureDate())
                .setMinimumOrbits(template.getOrbitsDeparture());

        FlightUiDTO.FlightLeg arrival = new FlightUiDTO.FlightLeg()
                .setRegion(template.getArrivalLocation().getRegion())
                .setLocation(template.getArrivalLocation().getName())
                .setDate(instance.getArrivalDate())
                .setMinimumOrbits(template.getOrbitsArrival());

        FlightUiDTO.CabinClassUi firstClass = new FlightUiDTO.CabinClassUi()
                .setTotal(vehicle.getFirstClass().getTotalSeats())
                .setAvailable(instance.getFirstClass().getAvailableSeats())
                .setPrice(instance.getFirstClass().getBasePrice())
                .setWindow(vehicle.getFirstClass().isWindowAvailable());

        FlightUiDTO.CabinClassUi middleClass = new FlightUiDTO.CabinClassUi()
                .setTotal(vehicle.getMiddleClass().getTotalSeats())
                .setAvailable(instance.getMiddleClass().getAvailableSeats())
                .setPrice(instance.getMiddleClass().getBasePrice())
                .setWindow(vehicle.getMiddleClass().isWindowAvailable());

        FlightUiDTO.CabinClassUi lowerClass = new FlightUiDTO.CabinClassUi()
                .setTotal(vehicle.getLowerClass().getTotalSeats())
                .setAvailable(instance.getLowerClass().getAvailableSeats())
                .setPrice(instance.getLowerClass().getBasePrice())
                .setWindow(vehicle.getLowerClass().isWindowAvailable());

        FlightUiDTO.FlightCabinsUi cabins = new FlightUiDTO.FlightCabinsUi()
                .setFirst(firstClass)
                .setMiddle(middleClass)
                .setLower(lowerClass);

        return new FlightUiDTO()
                .setId(instance.getPublicId())
                .setStatus(instance.getStatus())
                .setFlightNumber(template.getFlightNumber())
                .setEva(vehicle.getAmenities().contains(VehicleAmenity.EVA))
                .setDeparture(departure)
                .setArrival(arrival)
                .setTotalDurationMinutes(template.getDurationMinutes())
                .setRequiredCertifs(new String[]{})
                .setVehicleType(vehicle.getName())
                .setAmenities(vehicle.getAmenities())
                .setCabins(cabins)
                .setTotalSpacesAvailable(instance.getTotalSeatsAvailable());

    }
}
