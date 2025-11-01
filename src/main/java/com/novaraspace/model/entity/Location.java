package com.novaraspace.model.entity;

import com.novaraspace.model.enums.FlightLocation;
import com.novaraspace.model.enums.FlightRegion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Location {

    @Id
    private Long id;
    @Column(nullable = false)
    private FlightRegion region;
    @Column(nullable = false, unique = true)
    private FlightLocation location;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String longName;
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false, unique = true)
    private String locationNumber;

    public FlightRegion getRegion() {
        return region;
    }

    public Long getId() {
        return id;
    }

    public Location setId(Long id) {
        this.id = id;
        return this;
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

    public String getLocationNumber() {
        return locationNumber;
    }

    public Location setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
        return this;
    }
}
