package com.novaraspace.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novaraspace.factory.FlightJSONFactory;
import com.novaraspace.model.dto.flight.FlightTemplateGenerationRequest;
import com.novaraspace.model.embedded.CabinClassData;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.entity.FlightTemplate;
import com.novaraspace.model.entity.Vehicle;
import com.novaraspace.model.enums.FlightStatus;
import com.novaraspace.model.other.FlightGenerationProperties;
import com.novaraspace.model.other.FlightJSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FlightGenerationService {

    private FlightGenerationProperties flightProps;
    private final ObjectMapper mapper;
    private final FlightJSONFactory jsonFactory;

    public FlightGenerationService(ObjectMapper mapper, FlightJSONFactory jsonFactory) {
        this.mapper = mapper;
        this.jsonFactory = jsonFactory;

        this.initFlightProps();
    }

    private void initFlightProps() {
        ClassPathResource propsResource = new ClassPathResource("data/FlightGenerationProperties.json");
        try {
            this.flightProps = mapper.readValue(propsResource.getInputStream(), FlightGenerationProperties.class);
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: Make exceptions for this domain and throw here correctly?
        }
    }


    public FlightJSON generateNewFlightJSON(FlightTemplateGenerationRequest data) {
        return jsonFactory.generateNewFlightJSON(data);
    }


    public List<FlightInstance> generateInstancesForTemplate(FlightTemplate template, LocalDate startDate, LocalDate endDate) {
        List<FlightInstance> instances = new ArrayList<>();

        int[] weeklySchedule = Arrays.stream(template.getWeeklySchedule().split("")).mapToInt(Integer::parseInt).toArray();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            int dayOfWeek = date.getDayOfWeek().getValue();
            int currentDaySchedule = weeklySchedule[dayOfWeek - 1];
            if (shouldOperateOnDate(date, currentDaySchedule)) {
                FlightInstance instance = createInstanceWithDates(template, date);
                instances.add(instance);
            }
        }
        return instances;

    }



    private boolean shouldOperateOnDate(LocalDate date, int frequency) {
        if (frequency == 1) {return true;}
        else if (frequency == 0) {return false;};

        LocalDate anchorMonday = LocalDate.of(2020, 6, 1);
        LocalDate dateMonday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        long weeksSinceAnchor = ChronoUnit.WEEKS.between(anchorMonday, dateMonday);
        return weeksSinceAnchor % frequency == 0;
    }


    private FlightInstance createInstanceWithDates(FlightTemplate template, LocalDate departureDate) {
        LocalDateTime departureDateTime = LocalDateTime.of(departureDate, template.getDepartureTime());
        LocalDateTime arrivalDateTime = departureDateTime.plusMinutes(template.getDurationMinutes());

        Vehicle vehicle = template.getVehicle();
        double[] basePrices = calculatePrices(vehicle, template.getBasePrice());
        CabinClassData firstClass = new CabinClassData()
                .setAvailableSeats(vehicle.getFirstClass().getTotalSeats())
                .setLockedSeats(0)
                .setBasePrice(basePrices[2]);
        CabinClassData middleClass = new CabinClassData()
                .setAvailableSeats(vehicle.getMiddleClass().getTotalSeats())
                .setLockedSeats(0)
                .setBasePrice(basePrices[1]);
        CabinClassData lowerClass = new CabinClassData()
                .setAvailableSeats(vehicle.getLowerClass().getTotalSeats())
                .setLockedSeats(0)
                .setBasePrice(basePrices[0]);

        int totalSeats = firstClass.getAvailableSeats()
                + middleClass.getAvailableSeats()
                + lowerClass.getAvailableSeats();

        return new FlightInstance()
                .setPublicId(template.getPublicIdPrefix() + departureDate)
                .setStatus(FlightStatus.ON_TIME)
                .setFirstClass(firstClass)
                .setMiddleClass(middleClass)
                .setLowerClass(lowerClass)
                .setDepartureDate(departureDateTime)
                .setArrivalDate(arrivalDateTime)
                .setTotalSeatsAvailable(totalSeats)
                .setFlightTemplate(template);
    }

    private double[] calculatePrices(Vehicle vehicle, double basePrice) {
        double evaM = vehicle.isEva() ? flightProps.getEvaMultiplier() : 1.0;
        double galleyM = vehicle.isGalley() ? flightProps.getGalleyMultiplier() : 1.0;
        double loungeM = vehicle.isObservationLounge() ? flightProps.getLoungeMultiplier() : 1.0;
        double vrM = vehicle.isVr() ? flightProps.getVrMultiplier() : 1.0;

        double amenitiesMultiplier = evaM + galleyM + loungeM + vrM;

        double middleClassMultiplier = flightProps.getMiddleClassMultiplier();
        double firstClassMultiplier = flightProps.getFirstClassMultiplier();
        double classWindowMultiplier = flightProps.getClassWindowMultiplier();
        double lowerClassWindowM = vehicle.getLowerClass().isWindowAvailable() ? classWindowMultiplier : 1.0;
        double middleClassWindowM = vehicle.getMiddleClass().isWindowAvailable() ? classWindowMultiplier : 1.0;
        double firstClassWindowM = vehicle.getFirstClass().isWindowAvailable() ? classWindowMultiplier : 1.0;

        double lowerClassPrice = basePrice * (amenitiesMultiplier + lowerClassWindowM);
        double middleClassPrice = basePrice * (amenitiesMultiplier + middleClassWindowM + middleClassMultiplier);
        double firstClassPrice = basePrice * (amenitiesMultiplier + firstClassWindowM + firstClassMultiplier);
        return Arrays.stream(new double[] { lowerClassPrice, middleClassPrice, firstClassPrice }).map(this::normalizePrice).toArray();
    }

    private double normalizePrice(double price) {
        int intPrice = (int) Math.round(price);
        int remainder = intPrice % 10;
        if (remainder == 0) { return intPrice;}
        intPrice -= remainder;
        return intPrice;
    }
}
