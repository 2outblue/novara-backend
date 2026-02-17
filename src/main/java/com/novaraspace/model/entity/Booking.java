package com.novaraspace.model.entity;

import com.novaraspace.model.dto.flight.FlightReserveDTO;
import com.novaraspace.model.enums.CabinClassEnum;
import com.novaraspace.model.exception.BookingException;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Booking extends BaseEntity {
    @Column(unique = true)
    private String reference;
    @ManyToOne
    private FlightInstance departureFlight;
    private CabinClassEnum departureClass;
    private Double departureFlightPrice;
    @ManyToOne
    private FlightInstance returnFlight;
    private CabinClassEnum returnClass;
    private Double returnFlightPrice;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Passenger> passengers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExtraService> extraServices = new ArrayList<>();

    private String contactCountryCode;
    private String contactMobile;
    private String contactEmail;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private LocalDateTime createdAt;
    private boolean cancelled = false;

    public double getTotalPrice() {
        double servicesPrice = extraServices.stream()
                .map(ExtraService::getPrice)
                .mapToDouble(Double::doubleValue)
                .sum();

        double validReturnPrice = returnFlightPrice == null
                ? 0
                : returnFlightPrice;
        double validDeparturePrice = departureFlightPrice == null
                ? 0
                : departureFlightPrice;
        double flightsPrice = validDeparturePrice + validReturnPrice;
        return servicesPrice + flightsPrice;
    }

    public void cancel() {
        int paxCount = this.passengers.size();
        this.departureFlight.unReserveSeats(this.departureClass, paxCount);
        if (this.returnFlight != null) {
            this.returnFlight.unReserveSeats(this.returnClass, paxCount);
        }
        this.cancelled = true;
    }

    //TODO: Probably a way to combine the changeFlight methods?
    public void changeDepartureFlight(FlightInstance newFlight, FlightReserveDTO reserveDTO) {
        if (newFlight == null || reserveDTO == null) {throw BookingException.changeFailed();}
        CabinClassEnum newCabinClass = CabinClassEnum.getEnumFromDisplayName(reserveDTO.getFlightClass());
        if (newCabinClass == null || reserveDTO.getPrice() == null || reserveDTO.getPrice() <= 0) {
            throw BookingException.changeFailed();
        }
        int paxCount = this.passengers.size();
        this.departureFlight.unReserveSeats(this.departureClass, paxCount);
        this.setDepartureFlight(newFlight);
        this.setDepartureClass(newCabinClass);
        this.setDepartureFlightPrice(reserveDTO.getPrice());
        this.departureFlight.reserveSeats(newCabinClass, paxCount);
    }

    public void changeReturnFlight(FlightInstance newFlight, FlightReserveDTO reserveDTO) {
        if (this.returnFlight == null || newFlight == null || reserveDTO == null) {
            throw BookingException.changeFailed();
        }
        CabinClassEnum newCabinClass = CabinClassEnum.getEnumFromDisplayName(reserveDTO.getFlightClass());
        if (newCabinClass == null || reserveDTO.getPrice() == null || reserveDTO.getPrice() <= 0) {
            throw BookingException.changeFailed();
        }
        int paxCount = this.passengers.size();
        this.returnFlight.unReserveSeats(this.returnClass, paxCount);
        this.setReturnFlight(newFlight);
        this.setReturnClass(newCabinClass);
        this.setReturnFlightPrice(reserveDTO.getPrice());
        this.returnFlight.reserveSeats(newCabinClass, paxCount);
    }

//    TODO:
//    Already exists in the BookingValidator - if you need it here, move it
//    private boolean checkPaxBaggageMatchesServicePrice(Booking booking)

    public double getMinimumSeparationDays() {
        double departureSep = departureFlight.getFlightTemplate().getDepartureLocation().getRegion().getSeparationFactor();
        double arrivalSep = departureFlight.getFlightTemplate().getArrivalLocation().getRegion().getSeparationFactor();
        return Math.abs(departureSep - arrivalSep);
    }

    public String getReference() {
        return reference;
    }

    public Booking setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public FlightInstance getDepartureFlight() {
        return departureFlight;
    }

    public Booking setDepartureFlight(FlightInstance departureFlight) {
        this.departureFlight = departureFlight;
        return this;
    }

    public CabinClassEnum getDepartureClass() {
        return departureClass;
    }

    public Booking setDepartureClass(CabinClassEnum departureClass) {
        this.departureClass = departureClass;
        return this;
    }

    public Double getDepartureFlightPrice() {
        return departureFlightPrice;
    }

    public Booking setDepartureFlightPrice(Double departureFlightPrice) {
        this.departureFlightPrice = departureFlightPrice;
        return this;
    }

    public FlightInstance getReturnFlight() {
        return returnFlight;
    }

    public Booking setReturnFlight(FlightInstance returnFlight) {
        this.returnFlight = returnFlight;
        return this;
    }

    public CabinClassEnum getReturnClass() {
        return returnClass;
    }

    public Booking setReturnClass(CabinClassEnum returnClass) {
        this.returnClass = returnClass;
        return this;
    }

    public Double getReturnFlightPrice() {
        return returnFlightPrice;
    }

    public Booking setReturnFlightPrice(Double returnFlightPrice) {
        this.returnFlightPrice = returnFlightPrice;
        return this;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public Booking setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
        return this;
    }

    public List<ExtraService> getExtraServices() {
        return extraServices;
    }

    public Booking setExtraServices(List<ExtraService> extraServices) {
        this.extraServices = extraServices;
        return this;
    }

    public String getContactCountryCode() {
        return contactCountryCode;
    }

    public Booking setContactCountryCode(String contactCountryCode) {
        this.contactCountryCode = contactCountryCode;
        return this;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public Booking setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
        return this;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public Booking setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public Payment getPayment() {
        return payment;
    }

    public Booking setPayment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Booking setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public boolean isCancelled() {
        return cancelled;
    }

//    public Booking setCancelled(boolean cancelled) {
//        this.cancelled = cancelled;
//        return this;
//    }
}
