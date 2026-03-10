package com.novaraspace.model.entity;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class UserPaymentCard {
    private String reference;
    private String firstFour;
    private String lastFour;
    private String cardHolder;
    private String cardType;
    private String expiryDate;
    private LocalDate addedOn;

    public String getReference() {
        return reference;
    }

    public UserPaymentCard setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getFirstFour() {
        return firstFour;
    }

    public UserPaymentCard setFirstFour(String firstFour) {
        this.firstFour = firstFour;
        return this;
    }

    public String getLastFour() {
        return lastFour;
    }

    public UserPaymentCard setLastFour(String lastFour) {
        this.lastFour = lastFour;
        return this;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public UserPaymentCard setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
        return this;
    }

    public String getCardType() {
        return cardType;
    }

    public UserPaymentCard setCardType(String cardType) {
        this.cardType = cardType;
        return this;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public UserPaymentCard setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public LocalDate getAddedOn() {
        return addedOn;
    }

    public UserPaymentCard setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
        return this;
    }
}
