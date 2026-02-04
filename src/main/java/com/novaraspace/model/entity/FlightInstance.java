package com.novaraspace.model.entity;

import com.novaraspace.model.embedded.CabinClassData;
import com.novaraspace.model.enums.CabinClassEnum;
import com.novaraspace.model.enums.FlightStatus;
import com.novaraspace.model.exception.FlightException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class FlightInstance extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String publicId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @Embedded()
    @AttributeOverrides({
            @AttributeOverride(name = "availableSeats", column = @Column(name = "first_available_seats")),
            @AttributeOverride(name = "lockedSeats", column = @Column(name = "first_locked_seats")),
            @AttributeOverride(name = "basePrice", column = @Column(name = "first_base_price"))
    })
    private CabinClassData firstClass;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "availableSeats", column = @Column(name = "middle_available_seats")),
            @AttributeOverride(name = "lockedSeats", column = @Column(name = "middle_locked_seats")),
            @AttributeOverride(name = "basePrice", column = @Column(name = "middle_base_price"))
    })
    private CabinClassData middleClass;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "availableSeats", column = @Column(name = "lower_available_seats")),
            @AttributeOverride(name = "lockedSeats", column = @Column(name = "lower_locked_seats")),
            @AttributeOverride(name = "basePrice", column = @Column(name = "lower_base_price"))
    })
    private CabinClassData lowerClass;

    @Column(nullable = false)
    private LocalDateTime departureDate;
    @Column(nullable = false)
    private LocalDateTime arrivalDate;
    @Column(nullable = false)
    private int totalSeatsAvailable;

    @ManyToOne
    private FlightTemplate flightTemplate;

    public static FlightInstance from(FlightInstance flight) {
        return new FlightInstance()
                .setPublicId(flight.getPublicId())
                .setStatus(flight.getStatus())
                .setFirstClass(flight.getFirstClass())
                .setMiddleClass(flight.getMiddleClass())
                .setLowerClass(flight.getLowerClass())
                .setDepartureDate(flight.getDepartureDate())
                .setArrivalDate(flight.getArrivalDate())
                .setTotalSeatsAvailable(flight.getTotalSeatsAvailable())
                .setFlightTemplate(flight.getFlightTemplate());
    }

    public double getPrice(CabinClassEnum cabinClass) {
        return switch (cabinClass) {
            case FIRST -> firstClass.getBasePrice();
            case MIDDLE -> middleClass.getBasePrice();
            case LOWER -> lowerClass.getBasePrice();
        };
    }

    public boolean departsAtLeast3HoursFromNow() {
        LocalDateTime threeHoursFromNow = LocalDateTime.now().plusMinutes(179);
        return this.departureDate.isAfter(threeHoursFromNow);
    }

    //TODO: Cant you just get the correct cabin class with one switch statement, then do the rest without a second switch?
//    public void reserveSeats(CabinClassEnum cabinClass, int count) {
//        int unreservedSeats = switch (cabinClass) {
//            case FIRST -> firstClass.getAvailableSeats() - firstClass.getLockedSeats();
//            case MIDDLE -> middleClass.getAvailableSeats() - middleClass.getLockedSeats();
//            case LOWER -> lowerClass.getAvailableSeats() - lowerClass.getLockedSeats();
//        };
//        if (unreservedSeats < count) {throw FlightException.reservationFailed();}
//
//        switch (cabinClass) {
//            case FIRST -> firstClass.setAvailableSeats(firstClass.getAvailableSeats() - count);
//            case MIDDLE -> middleClass.setAvailableSeats(middleClass.getAvailableSeats() - count);
//            case LOWER -> lowerClass.setAvailableSeats(lowerClass.getAvailableSeats() - count);
//        }
//        recalculateTotalSeats();
//    }

    // TODO: Test this
    public void reserveSeats(CabinClassEnum cabinClass, int count) {
        if (cabinClass == null) {return;}
        CabinClassData selectedClass = getCabinClassData(cabinClass);
        int availableSeats = selectedClass.getAvailableSeats();
        int unreservedSeats = availableSeats - selectedClass.getLockedSeats();

        if (unreservedSeats < count) {throw FlightException.reservationFailed();}
        selectedClass.setAvailableSeats(availableSeats - count);
        recalculateTotalSeats();
    }

    public void unReserveSeats(CabinClassEnum cabinClass, int count) {
        if (cabinClass == null) {return;}
        CabinClassData selectedClass = this.getCabinClassData(cabinClass);
        Vehicle vehicle = this.flightTemplate.getVehicle();
        int cabinTotalSeats = vehicle
                .getClassByEnum(cabinClass)
                .getTotalSeats();

        int availableSeatsAfterUnReserve = selectedClass.getAvailableSeats() + count;
        if (availableSeatsAfterUnReserve > cabinTotalSeats) {
            //TODO: Throw some error here
            return;
        }
        selectedClass.setAvailableSeats(availableSeatsAfterUnReserve);
        recalculateTotalSeats();
    }

    public boolean departureDateIsBetween(LocalDate lowerDate, LocalDate upperDate) {
        boolean notBeforeLower = departureDate.isAfter(lowerDate.minusDays(1).atTime(LocalTime.MAX));
        boolean notAfterUpper = departureDate.isBefore(upperDate.plusDays(1).atTime(LocalTime.MIN));
        return notBeforeLower && notAfterUpper;
    }

    public CabinClassData getCabinClassData(CabinClassEnum cabinEnum) {
        return switch (cabinEnum) {
            case FIRST -> this.getFirstClass();
            case MIDDLE -> this.getMiddleClass();
            case LOWER -> this.getLowerClass();
        };
    }

    private void recalculateTotalSeats() {
        totalSeatsAvailable = firstClass.getAvailableSeats()
                + middleClass.getAvailableSeats()
                + lowerClass.getAvailableSeats();
    }

    public String getPublicId() {
        return publicId;
    }

    public FlightInstance setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public FlightInstance setStatus(FlightStatus status) {
        this.status = status;
        return this;
    }

    public CabinClassData getFirstClass() {
        return firstClass;
    }

    public FlightInstance setFirstClass(CabinClassData firstClass) {
        this.firstClass = firstClass;
        return this;
    }

    public CabinClassData getMiddleClass() {
        return middleClass;
    }

    public FlightInstance setMiddleClass(CabinClassData middleClass) {
        this.middleClass = middleClass;
        return this;
    }

    public CabinClassData getLowerClass() {
        return lowerClass;
    }

    public FlightInstance setLowerClass(CabinClassData lowerClass) {
        this.lowerClass = lowerClass;
        return this;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public FlightInstance setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public FlightInstance setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public int getTotalSeatsAvailable() {
        return totalSeatsAvailable;
    }

    public FlightInstance setTotalSeatsAvailable(int totalSeatsAvailable) {
        this.totalSeatsAvailable = totalSeatsAvailable;
        return this;
    }

    public FlightTemplate getFlightTemplate() {
        return flightTemplate;
    }

    public FlightInstance setFlightTemplate(FlightTemplate flightTemplate) {
        this.flightTemplate = flightTemplate;
        return this;
    }
}
