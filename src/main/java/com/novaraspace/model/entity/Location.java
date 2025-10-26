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
    @Column(nullable = false)
    private String longName;
    @Column(nullable = false)
    private String code;


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

    public String getLongName() {
        return longName;
    }

    public Location setLongName(String longName) {
        this.longName = longName;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Location setCode(String code) {
        this.code = code;
        return this;
    }
}
