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

    private String nameDetails;
    private String desc;
    private String type;
    private long capacity;
    private long area;
    private long volume;
    private long ports;
    private long accommodations;

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

    public String getNameDetails() {
        return nameDetails;
    }

    public Location setNameDetails(String nameDetails) {
        this.nameDetails = nameDetails;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Location setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getType() {
        return type;
    }

    public Location setType(String type) {
        this.type = type;
        return this;
    }

    public long getCapacity() {
        return capacity;
    }

    public Location setCapacity(long capacity) {
        this.capacity = capacity;
        return this;
    }

    public long getArea() {
        return area;
    }

    public Location setArea(long area) {
        this.area = area;
        return this;
    }

    public long getVolume() {
        return volume;
    }

    public Location setVolume(long volume) {
        this.volume = volume;
        return this;
    }

    public long getPorts() {
        return ports;
    }

    public Location setPorts(long ports) {
        this.ports = ports;
        return this;
    }

    public long getAccommodations() {
        return accommodations;
    }

    public Location setAccommodations(long accommodations) {
        this.accommodations = accommodations;
        return this;
    }
}
