package com.novaraspace.model.dto.user;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddCardDTO {
    @NotBlank
    @Size(min = 4, max = 4)
    @Pattern(regexp = "^[0-9]+$")
    private String firstFour;
    @NotBlank
    @Size(min = 4, max = 4)
    @Pattern(regexp = "^[0-9]+$")
    private String lastFour;
    @NotBlank
    @Size(min = 3, max = 30)
    private String cardHolder;
    @NotBlank
    @Size(min = 5, max = 5)
    @Pattern(regexp = "^[0-9]{2}/[0-9]{2}$")
    private String expiryDate;

    public String getFirstFour() {
        return firstFour;
    }

    public AddCardDTO setFirstFour(String firstFour) {
        this.firstFour = firstFour;
        return this;
    }

    public String getLastFour() {
        return lastFour;
    }

    public AddCardDTO setLastFour(String lastFour) {
        this.lastFour = lastFour;
        return this;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public AddCardDTO setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
        return this;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public AddCardDTO setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }
}
