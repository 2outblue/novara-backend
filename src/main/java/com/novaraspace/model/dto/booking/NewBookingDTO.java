package com.novaraspace.model.dto.booking;

import com.novaraspace.validation.annotations.ValidPassengerIntraIds;
import com.novaraspace.validation.annotations.ValidReturnFlightComponents;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

@ValidPassengerIntraIds
@ValidReturnFlightComponents
public class NewBookingDTO {
    //TODO: Replace with the FlightReserveDTO i guess.
    @NotEmpty
    private String departureFlightId;
    @NotEmpty
    private String departureFlightClass;
    @NotNull
    @Positive
    private Double departureFlightPrice;
    private String returnFlightId;
    private String returnFlightClass;
    private Double returnFlightPrice;
    @Valid
    private List<PassengerDTO> passengers;
    @Valid
    private NewBookingContactDetails contactDetails;
    @Valid
    private List<ExtraServiceDTO> extraServices;

//    @NotEmpty
//    @Email
//    private String billingEmail;
//    @NotEmpty
//    @Size(min = 5, max = 15)
//    private String billingMobile;
//    @NotEmpty
//    private String cardHolder;
//    @NotEmpty
//    @Size(min = 4, max = 4)
//    private String lastFourDigitsCard;

    @NotEmpty
    private String quoteReference;

    public String getDepartureFlightId() {
        return departureFlightId;
    }

    public NewBookingDTO setDepartureFlightId(String departureFlightId) {
        this.departureFlightId = departureFlightId;
        return this;
    }

    public String getDepartureFlightClass() {
        return departureFlightClass;
    }

    public NewBookingDTO setDepartureFlightClass(String departureFlightClass) {
        this.departureFlightClass = departureFlightClass;
        return this;
    }

    public Double getDepartureFlightPrice() {
        return departureFlightPrice;
    }

    public NewBookingDTO setDepartureFlightPrice(Double departureFlightPrice) {
        this.departureFlightPrice = departureFlightPrice;
        return this;
    }

    public String getReturnFlightId() {
        return returnFlightId;
    }

    public NewBookingDTO setReturnFlightId(String returnFlightId) {
        this.returnFlightId = returnFlightId;
        return this;
    }

    public String getReturnFlightClass() {
        return returnFlightClass;
    }

    public NewBookingDTO setReturnFlightClass(String returnFlightClass) {
        this.returnFlightClass = returnFlightClass;
        return this;
    }

    public Double getReturnFlightPrice() {
        return returnFlightPrice;
    }

    public NewBookingDTO setReturnFlightPrice(Double returnFlightPrice) {
        this.returnFlightPrice = returnFlightPrice;
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

//    public String getBillingEmail() {
//        return billingEmail;
//    }
//
//    public NewBookingDTO setBillingEmail(String billingEmail) {
//        this.billingEmail = billingEmail;
//        return this;
//    }
//
//    public String getBillingMobile() {
//        return billingMobile;
//    }
//
//    public NewBookingDTO setBillingMobile(String billingMobile) {
//        this.billingMobile = billingMobile;
//        return this;
//    }
//
//    public String getCardHolder() {
//        return cardHolder;
//    }
//
//    public NewBookingDTO setCardHolder(String cardHolder) {
//        this.cardHolder = cardHolder;
//        return this;
//    }
//
//    public String getLastFourDigitsCard() {
//        return lastFourDigitsCard;
//    }
//
//    public NewBookingDTO setLastFourDigitsCard(String lastFourDigitsCard) {
//        this.lastFourDigitsCard = lastFourDigitsCard;
//        return this;
//    }

    public String getQuoteReference() {
        return quoteReference;
    }

    public NewBookingDTO setQuoteReference(String quoteReference) {
        this.quoteReference = quoteReference;
        return this;
    }
}
