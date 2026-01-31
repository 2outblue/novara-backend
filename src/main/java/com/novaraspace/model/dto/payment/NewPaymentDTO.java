package com.novaraspace.model.dto.payment;

import jakarta.validation.constraints.*;

public class NewPaymentDTO {
    @NotBlank
    @Size(min = 4, max = 4)
    @Pattern(regexp = "^[0-9]+$")
    private String firstFour;
    @NotBlank
    @Size(min = 4, max = 4)
    @Pattern(regexp = "^[0-9]+$")
    private String lastFour;
    private String phoneNumber;
    @Email
    private String email;
    @NotBlank
    private String cardHolder;
    @NotNull
    @Positive
    private Double amount;

    public String getFirstFour() {
        return firstFour;
    }

    public NewPaymentDTO setFirstFour(String firstFour) {
        this.firstFour = firstFour;
        return this;
    }

    public String getLastFour() {
        return lastFour;
    }

    public NewPaymentDTO setLastFour(String lastFour) {
        this.lastFour = lastFour;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public NewPaymentDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public NewPaymentDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public NewPaymentDTO setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public NewPaymentDTO setAmount(Double amount) {
        this.amount = amount;
        return this;
    }
}

