package com.novaraspace.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class ExtraService extends BaseEntity {
    private String code;
    private double price;

    @ManyToOne
    private Booking booking;
}
