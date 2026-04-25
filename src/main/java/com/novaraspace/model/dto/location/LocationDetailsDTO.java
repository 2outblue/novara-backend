package com.novaraspace.model.dto.location;

import com.novaraspace.model.enums.FlightRegion;

public class LocationDetailsDTO {

    private String name;
    private String longName;
    private String code;
    private String nameDetails;
    private String locationNumber;
    private String description;
    private String locationType;
    private long capacity;
    private long area;
    private long ports;

    public String getName() {
        return name;
    }

    public LocationDetailsDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getLongName() {
        return longName;
    }

    public LocationDetailsDTO setLongName(String longName) {
        this.longName = longName;
        return this;
    }

    public String getCode() {
        return code;
    }

    public LocationDetailsDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public String getNameDetails() {
        return nameDetails;
    }

    public LocationDetailsDTO setNameDetails(String nameDetails) {
        this.nameDetails = nameDetails;
        return this;
    }

    public String getLocationNumber() {
        return locationNumber;
    }

    public LocationDetailsDTO setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LocationDetailsDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLocationType() {
        return locationType;
    }

    public LocationDetailsDTO setLocationType(String locationType) {
        this.locationType = locationType;
        return this;
    }

    public long getCapacity() {
        return capacity;
    }

    public LocationDetailsDTO setCapacity(long capacity) {
        this.capacity = capacity;
        return this;
    }

    public long getArea() {
        return area;
    }

    public LocationDetailsDTO setArea(long area) {
        this.area = area;
        return this;
    }

    public long getPorts() {
        return ports;
    }

    public LocationDetailsDTO setPorts(long ports) {
        this.ports = ports;
        return this;
    }
}
