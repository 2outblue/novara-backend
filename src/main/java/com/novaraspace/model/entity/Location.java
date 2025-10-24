package com.novaraspace.model.entity;

import com.novaraspace.model.enums.FlightLocation;
import com.novaraspace.model.enums.FlightRegion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Location extends BaseEntity {

    @Column(nullable = false)
    private FlightRegion region;
    @Column(nullable = false)
    private FlightLocation location;
    @Column(nullable = false)
    private String name;


    public FlightRegion getRegion() {
        return region;
    }

    public Location setRegion(FlightRegion region) {
        this.region = region;
        return this;
    }

    public FlightLocation getLocation() {
        return location;
    }

    public Location setLocation(FlightLocation location) {
        this.location = location;
        return this;
    }

    public String getName() {
        return name;
    }

    public Location setName(String name) {
        this.name = name;
        return this;
    }
}
