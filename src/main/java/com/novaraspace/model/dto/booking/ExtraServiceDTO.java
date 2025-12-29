package com.novaraspace.model.dto.booking;

import com.novaraspace.validation.annotations.ValidExtraServiceCode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public class ExtraServiceDTO {
    @NotEmpty
    @ValidExtraServiceCode
    private String code;

    @Positive
    private double price;

    public String getCode() {
        return code;
    }

    public ExtraServiceDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public ExtraServiceDTO setPrice(double price) {
        this.price = price;
        return this;
    }
}
