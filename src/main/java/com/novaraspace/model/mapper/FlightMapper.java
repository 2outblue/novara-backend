package com.novaraspace.model.mapper;

import com.novaraspace.model.dto.flight.BookedFlightDTO;
import com.novaraspace.model.dto.flight.FlightLeg;
import com.novaraspace.model.dto.flight.FlightScheduleDTO;
import com.novaraspace.model.dto.flight.FlightUiDTO;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.entity.FlightTemplate;
import com.novaraspace.model.entity.Location;
import com.novaraspace.model.entity.Vehicle;
import com.novaraspace.model.enums.FlightRegion;
import com.novaraspace.model.enums.VehicleAmenity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class FlightMapper {

    public BookedFlightDTO instanceToBookedFlightDTO(FlightInstance instance) {
        FlightTemplate template = instance.getFlightTemplate();
        String vehicleName = template.getVehicle().getName();

        FlightLeg departureLeg = mapFlightLegFromInstance(instance, true);
        FlightLeg arrivalLeg = mapFlightLegFromInstance(instance, false);

        return new BookedFlightDTO()
                .setId(instance.getPublicId())
                .setFlightNumber(template.getFlightNumber())
                .setDeparture(departureLeg)
                .setArrival(arrivalLeg)
                .setDurationMinutes(template.getDurationMinutes())
                .setVehicleType(vehicleName)
                .setSelectedClass(null)
                .setPrice(0.0);
    }

    public FlightScheduleDTO instanceToScheduleDTO(FlightInstance instance) {
        FlightTemplate template = instance.getFlightTemplate();
        String vehicleName = template.getVehicle().getName();

        String schedule = template.getWeeklySchedule();
        String regularity = schedule.equals("1111111")
                ? "Daily flight"
                : "Non-daily flight";

        return new FlightScheduleDTO()
                .setVehicleName(vehicleName)
                .setFlightNumber(template.getFlightNumber())
                .setRegularity(regularity)
                .setDepartDate(instance.getDepartureDate())
                .setArrivalDate(instance.getArrivalDate())
                .setDepartCode(template.getDepartureLocation().getCode())
                .setArriveCode(template.getArrivalLocation().getCode());
    }

    public FlightUiDTO instanceToFlightUiDTO(FlightInstance instance) {
        FlightTemplate template = instance.getFlightTemplate();
        Vehicle vehicle = template.getVehicle();

        FlightLeg departure = mapFlightLegFromInstance(instance, true);
        FlightLeg arrival = mapFlightLegFromInstance(instance, false);

//        FlightLeg departure = new FlightLeg()
//                .setLocationCode(template.getDepartureLocation().getCode())
//                .setRegion(template.getDepartureLocation().getRegion())
//                .setLocation(template.getDepartureLocation().getName())
//                .setDate(instance.getDepartureDate())
//                .setMinimumOrbits(template.getOrbitsDeparture());
//
//        FlightLeg arrival = new FlightLeg()
//                .setLocationCode(template.getArrivalLocation().getCode())
//                .setRegion(template.getArrivalLocation().getRegion())
//                .setLocation(template.getArrivalLocation().getName())
//                .setDate(instance.getArrivalDate())
//                .setMinimumOrbits(template.getOrbitsArrival());

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

    protected FlightLeg mapFlightLegFromInstance(FlightInstance instance, boolean departure) {
        FlightTemplate template = instance.getFlightTemplate();
        Location location = departure
                ? template.getDepartureLocation()
                : template.getArrivalLocation();

        String locationCode = location.getCode();
        FlightRegion region = location.getRegion();
        String locationName = location.getName();
        LocalDateTime date = departure
                ? instance.getDepartureDate()
                : instance.getArrivalDate();
        int minimumOrbits = departure
                ? template.getOrbitsDeparture()
                : template.getOrbitsArrival();

        return new FlightLeg()
                .setRegion(region)
                .setLocation(locationName)
                .setLocationCode(locationCode)
                .setDate(date)
                .setMinimumOrbits(minimumOrbits);
    }

}
