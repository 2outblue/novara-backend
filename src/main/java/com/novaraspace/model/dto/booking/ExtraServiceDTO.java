package com.novaraspace.model.dto.booking;

import com.novaraspace.model.enums.ExtraServiceCode;
import jakarta.validation.constraints.*;

public class ExtraServiceDTO {
    @NotNull
    private ExtraServiceCode code;

    @Positive
    private double price;

    public ExtraServiceCode getCode() {
        return code;
    }

    public ExtraServiceDTO setCode(ExtraServiceCode code) {
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
