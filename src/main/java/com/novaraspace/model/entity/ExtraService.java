package com.novaraspace.model.entity;

import com.novaraspace.model.enums.ExtraServiceCode;
import jakarta.persistence.Embeddable;

@Embeddable
public class ExtraService {
    private ExtraServiceCode code;
    private double price;

    public ExtraServiceCode getCode() {
        return code;
    }

    public ExtraService setCode(ExtraServiceCode code) {
        this.code = code;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public ExtraService setPrice(double price) {
        this.price = price;
        return this;
    }

}
