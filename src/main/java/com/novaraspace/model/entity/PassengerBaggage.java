package com.novaraspace.model.entity;

import jakarta.persistence.Entity;

@Entity
public class PassengerBaggage extends BaseEntity {
    private String capacity;
    private double price;
}
