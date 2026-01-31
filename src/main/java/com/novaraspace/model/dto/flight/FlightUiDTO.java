package com.novaraspace.model.dto.flight;

import com.novaraspace.model.enums.FlightRegion;
import com.novaraspace.model.enums.FlightStatus;
import com.novaraspace.model.enums.VehicleAmenity;

import java.time.LocalDateTime;
import java.util.Set;

//TODO: Rename this to something like AvailableFlightDTO or something like that
// Its not a general UI model as it contains the cabin classes with available seats,
// and other data which is used only on flight selection...
public class FlightUiDTO {
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
    private FlightCabinsUi cabins;
    private int totalSpacesAvailable;

    public static class FlightCabinsUi {
        private CabinClassUi first;
        private CabinClassUi middle;
        private CabinClassUi lower;

        public CabinClassUi getFirst() {
            return first;
        }

        public FlightCabinsUi setFirst(CabinClassUi first) {
            this.first = first;
            return this;
        }

        public CabinClassUi getMiddle() {
            return middle;
        }

        public FlightCabinsUi setMiddle(CabinClassUi middle) {
            this.middle = middle;
            return this;
        }

        public CabinClassUi getLower() {
            return lower;
        }

        public FlightCabinsUi setLower(CabinClassUi lower) {
            this.lower = lower;
            return this;
        }
    }

    public static class CabinClassUi {
        private int total;
        private int available;
        private double price;
        private boolean window;

        public int getTotal() {
            return total;
        }

        public CabinClassUi setTotal(int total) {
            this.total = total;
            return this;
        }

        public int getAvailable() {
            return available;
        }

        public CabinClassUi setAvailable(int available) {
            this.available = available;
            return this;
        }

        public double getPrice() {
            return price;
        }

        public CabinClassUi setPrice(double price) {
            this.price = price;
            return this;
        }

        public boolean isWindow() {
            return window;
        }

        public CabinClassUi setWindow(boolean window) {
            this.window = window;
            return this;
        }
    }

//    public static class FlightLeg {
//        private FlightRegion region;
//        private String location;
//        private LocalDateTime date;
//        private int minimumOrbits;
//
//        public FlightRegion getRegion() {
//            return region;
//        }
//
//        public FlightLeg setRegion(FlightRegion region) {
//            this.region = region;
//            return this;
//        }
//
//        public String getLocation() {
//            return location;
//        }
//
//        public FlightLeg setLocation(String location) {
//            this.location = location;
//            return this;
//        }
//
//        public LocalDateTime getDate() {
//            return date;
//        }
//
//        public FlightLeg setDate(LocalDateTime date) {
//            this.date = date;
//            return this;
//        }
//
//        public int getMinimumOrbits() {
//            return minimumOrbits;
//        }
//
//        public FlightLeg setMinimumOrbits(int minimumOrbits) {
//            this.minimumOrbits = minimumOrbits;
//            return this;
//        }
//    }

    public String getId() {
        return id;
    }

    public FlightUiDTO setId(String id) {
        this.id = id;
        return this;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public FlightUiDTO setStatus(FlightStatus status) {
        this.status = status;
        return this;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public FlightUiDTO setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public boolean isEva() {
        return eva;
    }

    public FlightUiDTO setEva(boolean eva) {
        this.eva = eva;
        return this;
    }

    public FlightLeg getDeparture() {
        return departure;
    }

    public FlightUiDTO setDeparture(FlightLeg departure) {
        this.departure = departure;
        return this;
    }

    public FlightLeg getArrival() {
        return arrival;
    }

    public FlightUiDTO setArrival(FlightLeg arrival) {
        this.arrival = arrival;
        return this;
    }

    public int getTotalDurationMinutes() {
        return totalDurationMinutes;
    }

    public FlightUiDTO setTotalDurationMinutes(int totalDurationMinutes) {
        this.totalDurationMinutes = totalDurationMinutes;
        return this;
    }

    public String[] getRequiredCertifs() {
        return requiredCertifs;
    }

    public FlightUiDTO setRequiredCertifs(String[] requiredCertifs) {
        this.requiredCertifs = requiredCertifs;
        return this;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public FlightUiDTO setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public Set<VehicleAmenity> getAmenities() {
        return amenities;
    }

    public FlightUiDTO setAmenities(Set<VehicleAmenity> amenities) {
        this.amenities = amenities;
        return this;
    }

    public FlightCabinsUi getCabins() {
        return cabins;
    }

    public FlightUiDTO setCabins(FlightCabinsUi cabins) {
        this.cabins = cabins;
        return this;
    }

    public int getTotalSpacesAvailable() {
        return totalSpacesAvailable;
    }

    public FlightUiDTO setTotalSpacesAvailable(int totalSpacesAvailable) {
        this.totalSpacesAvailable = totalSpacesAvailable;
        return this;
    }
}
