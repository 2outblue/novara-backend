package com.novaraspace.model.dto.flight;

import com.novaraspace.model.enums.FlightStatus;
import com.novaraspace.model.enums.VehicleAmenity;

import java.util.Set;

public class AvailableFlightDTO {
    private String id;
    private FlightStatus status;
    private String flightNumber;
    private boolean eva;
    private FlightLeg departure;
    private FlightLeg arrival;
    private int totalDurationMinutes;
    private String[] requiredCertifs;
    private String vehicleType;
    private Set<VehicleAmenity> amenities;
    private AvailableCabinsDTO cabins;
    private int totalSpacesAvailable;

    public static class AvailableCabinsDTO {
        private AvailableCabinClassDTO first;
        private AvailableCabinClassDTO middle;
        private AvailableCabinClassDTO lower;

        public AvailableCabinClassDTO getFirst() {
            return first;
        }

        public AvailableCabinsDTO setFirst(AvailableCabinClassDTO first) {
            this.first = first;
            return this;
        }

        public AvailableCabinClassDTO getMiddle() {
            return middle;
        }

        public AvailableCabinsDTO setMiddle(AvailableCabinClassDTO middle) {
            this.middle = middle;
            return this;
        }

        public AvailableCabinClassDTO getLower() {
            return lower;
        }

        public AvailableCabinsDTO setLower(AvailableCabinClassDTO lower) {
            this.lower = lower;
            return this;
        }
    }

    public static class AvailableCabinClassDTO {
        private int total;
        private int available;
        private double price;
        private boolean window;

        public int getTotal() {
            return total;
        }

        public AvailableCabinClassDTO setTotal(int total) {
            this.total = total;
            return this;
        }

        public int getAvailable() {
            return available;
        }

        public AvailableCabinClassDTO setAvailable(int available) {
            this.available = available;
            return this;
        }

        public double getPrice() {
            return price;
        }

        public AvailableCabinClassDTO setPrice(double price) {
            this.price = price;
            return this;
        }

        public boolean isWindow() {
            return window;
        }

        public AvailableCabinClassDTO setWindow(boolean window) {
            this.window = window;
            return this;
        }
    }


    public String getId() {
        return id;
    }

    public AvailableFlightDTO setId(String id) {
        this.id = id;
        return this;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public AvailableFlightDTO setStatus(FlightStatus status) {
        this.status = status;
        return this;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public AvailableFlightDTO setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public boolean isEva() {
        return eva;
    }

    public AvailableFlightDTO setEva(boolean eva) {
        this.eva = eva;
        return this;
    }

    public FlightLeg getDeparture() {
        return departure;
    }

    public AvailableFlightDTO setDeparture(FlightLeg departure) {
        this.departure = departure;
        return this;
    }

    public FlightLeg getArrival() {
        return arrival;
    }

    public AvailableFlightDTO setArrival(FlightLeg arrival) {
        this.arrival = arrival;
        return this;
    }

    public int getTotalDurationMinutes() {
        return totalDurationMinutes;
    }

    public AvailableFlightDTO setTotalDurationMinutes(int totalDurationMinutes) {
        this.totalDurationMinutes = totalDurationMinutes;
        return this;
    }

    public String[] getRequiredCertifs() {
        return requiredCertifs;
    }

    public AvailableFlightDTO setRequiredCertifs(String[] requiredCertifs) {
        this.requiredCertifs = requiredCertifs;
        return this;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public AvailableFlightDTO setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public Set<VehicleAmenity> getAmenities() {
        return amenities;
    }

    public AvailableFlightDTO setAmenities(Set<VehicleAmenity> amenities) {
        this.amenities = amenities;
        return this;
    }

    public AvailableCabinsDTO getCabins() {
        return cabins;
    }

    public AvailableFlightDTO setCabins(AvailableCabinsDTO cabins) {
        this.cabins = cabins;
        return this;
    }

    public int getTotalSpacesAvailable() {
        return totalSpacesAvailable;
    }

    public AvailableFlightDTO setTotalSpacesAvailable(int totalSpacesAvailable) {
        this.totalSpacesAvailable = totalSpacesAvailable;
        return this;
    }
}
