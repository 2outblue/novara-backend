package com.novaraspace.model.dto.booking;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class NewBookingDTO {
    @NotEmpty
    private String departureFlightId;
    private String returnFlightId;
    private List<NewPassengerDTO> passengers;
    private NewBookingContactDetails contactDetails;
    private List<ExtraServiceDTO> extraServices;

    @NotEmpty
    @Email
    private String billingEmail;
    @NotEmpty
    @Size(min = 5, max = 15)
    private String billingMobile;

    public String getDepartureFlightId() {
        return departureFlightId;
    }

    public NewBookingDTO setDepartureFlightId(String departureFlightId) {
        this.departureFlightId = departureFlightId;
        return this;
    }

    public String getReturnFlightId() {
        return returnFlightId;
    }

    public NewBookingDTO setReturnFlightId(String returnFlightId) {
        this.returnFlightId = returnFlightId;
        return this;
    }

    public List<NewPassengerDTO> getPassengers() {
        return passengers;
    }

    public NewBookingDTO setPassengers(List<NewPassengerDTO> passengers) {
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

    public String getBillingEmail() {
        return billingEmail;
    }

    public NewBookingDTO setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
        return this;
    }

    public String getBillingMobile() {
        return billingMobile;
    }

    public NewBookingDTO setBillingMobile(String billingMobile) {
        this.billingMobile = billingMobile;
        return this;
    }
}
