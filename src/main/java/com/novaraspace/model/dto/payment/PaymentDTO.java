package com.novaraspace.model.dto.payment;

import java.time.LocalDateTime;

public class PaymentDTO {
    private String lastFour;
    private String cardType;
    private String serviceReference;
    private Double amount;
    private LocalDateTime createdAt;

    public String getLastFour() {
        return lastFour;
    }

    public PaymentDTO setLastFour(String lastFour) {
        this.lastFour = lastFour;
        return this;
    }

    public String getCardType() {
        return cardType;
    }

    public PaymentDTO setCardType(String cardType) {
        this.cardType = cardType;
        return this;
    }

    public String getServiceReference() {
        return serviceReference;
    }

    public PaymentDTO setServiceReference(String serviceReference) {
        this.serviceReference = serviceReference;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public PaymentDTO setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public PaymentDTO setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
