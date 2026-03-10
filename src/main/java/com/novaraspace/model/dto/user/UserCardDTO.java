package com.novaraspace.model.dto.user;

import java.time.LocalDate;

public class UserCardDTO {
    private String reference;
    private String lastFour;
    private String cardHolder;
    private String cardType;
    private String expiryDate;

    public String getReference() {
        return reference;
    }

    public UserCardDTO setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getLastFour() {
        return lastFour;
    }

    public UserCardDTO setLastFour(String lastFour) {
        this.lastFour = lastFour;
        return this;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public UserCardDTO setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
        return this;
    }

    public String getCardType() {
        return cardType;
    }

    public UserCardDTO setCardType(String cardType) {
        this.cardType = cardType;
        return this;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public UserCardDTO setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }
}
