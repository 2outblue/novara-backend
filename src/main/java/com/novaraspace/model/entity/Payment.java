package com.novaraspace.model.entity;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Payment extends BaseEntity{
    private String reference;
    private String serviceReference;

    private String firstFour;
    private String lastFour;
    private String cardHolder;
    private String cardType;

    private String email;
    private String phoneNumber;

    private LocalDateTime createdAt;

    public String getReference() {
        return reference;
    }

    public Payment setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getServiceReference() {
        return serviceReference;
    }

    public Payment setServiceReference(String serviceReference) {
        this.serviceReference = serviceReference;
        return this;
    }

    public String getFirstFour() {
        return firstFour;
    }

    public Payment setFirstFour(String firstFour) {
        this.firstFour = firstFour;
        return this;
    }

    public String getLastFour() {
        return lastFour;
    }

    public Payment setLastFour(String lastFour) {
        this.lastFour = lastFour;
        return this;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public Payment setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
        return this;
    }

    public String getCardType() {
        return cardType;
    }

    public Payment setCardType(String cardType) {
        this.cardType = cardType;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Payment setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Payment setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Payment setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
