package com.novaraspace.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

//@Entity
@Embeddable
public class ExtraService {
    private String code;
    private double price;

    public String getCode() {
        return code;
    }

    public ExtraService setCode(String code) {
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
