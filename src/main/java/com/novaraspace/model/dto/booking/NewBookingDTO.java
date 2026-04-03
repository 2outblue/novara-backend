package com.novaraspace.model.dto.booking;

import com.novaraspace.model.dto.flight.FlightReserveDTO;
import com.novaraspace.validation.annotations.ValidPassengerIntraIds;
import com.novaraspace.validation.annotations.ValidReturnFlightComponents;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

@ValidPassengerIntraIds
public class NewBookingDTO {
    @Valid
    @NotNull
    private FlightReserveDTO departureFlight;
    @Valid
    private FlightReserveDTO returnFlight;
    @Valid
    @Size(min = 1, max = 11)
    private List<PassengerDTO> passengers;
    @Valid
    private NewBookingContactDetails contactDetails;
    @Valid
    @Size(max = 6)
    private List<ExtraServiceDTO> extraServices;

    @NotBlank
    @Size(min = 20, max = 120)
    private String quoteReference;

    public String getDepartureFlightId() {
        return departureFlight.getId();
    }
    public String getDepartureFlightClass() {
        return departureFlight.getFlightClass();
    }
    public Double getDepartureFlightPrice() {
        return departureFlight.getPrice();
    }

    public String getReturnFlightId() {
        return returnFlight == null ? "" : returnFlight.getId();
    }
    public String getReturnFlightClass() {
        return returnFlight == null ? "" : returnFlight.getFlightClass();
    }
    public Double getReturnFlightPrice() {
        return returnFlight == null ? 0.0 : returnFlight.getPrice();
    }

    public FlightReserveDTO getDepartureFlight() {
        return departureFlight;
    }

    public NewBookingDTO setDepartureFlight(FlightReserveDTO departureFlight) {
        this.departureFlight = departureFlight;
        return this;
    }

    public FlightReserveDTO getReturnFlight() {
        return returnFlight;
    }

    public NewBookingDTO setReturnFlight(FlightReserveDTO returnFlight) {
        this.returnFlight = returnFlight;
        return this;
    }

    public List<PassengerDTO> getPassengers() {
        return passengers;
    }

    public NewBookingDTO setPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
        return this;
    }

    public NewBookingContactDetails getContactDetails() {
        return contactDetails;
    }

    public NewBookingDTO setContactDetails(NewBookingContactDetails contactDetails) {
        this.contactDetails = contactDetails;
        return this;
    }

    public List<ExtraServiceDTO> getExtraServices() {
        return extraServices;
    }

    public NewBookingDTO setExtraServices(List<ExtraServiceDTO> extraServices) {
        this.extraServices = extraServices;
        return this;
    }


    public String getQuoteReference() {
        return quoteReference;
    }

    public NewBookingDTO setQuoteReference(String quoteReference) {
        this.quoteReference = quoteReference;
        return this;
    }
}
