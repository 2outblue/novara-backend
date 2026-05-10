package com.novaraspace.model.dto.booking;

import com.novaraspace.model.enums.ExtraServiceCode;
import com.novaraspace.validation.annotations.ValidExtraServiceCode;
import jakarta.validation.constraints.*;

public class ExtraServiceDTO {
//    @NotBlank
//    @Size(min = 1, max = 30)
//    @ValidExtraServiceCode
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
