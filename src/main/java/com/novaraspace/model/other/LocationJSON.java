package com.novaraspace.model.other;

public class LocationJSON {
    private long id;
    private String location;
    private String name;
    private String longName;
    private String code;
    private String locationNumber;
    private String nameDetails;
    private String desc;
    private String type;
    private long capacity;
    private long area;
    private long volume;
    private long ports;
    private long accommodations;

    public long getId() {
        return id;
    }

    public LocationJSON setId(long id) {
        this.id = id;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public LocationJSON setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getName() {
        return name;
    }

    public LocationJSON setName(String name) {
        this.name = name;
        return this;
    }

    public String getLongName() {
        return longName;
    }

    public LocationJSON setLongName(String longName) {
        this.longName = longName;
        return this;
    }

    public String getCode() {
        return code;
    }

    public LocationJSON setCode(String code) {
        this.code = code;
        return this;
    }

    public String getLocationNumber() {
        return locationNumber;
    }

    public LocationJSON setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
        return this;
    }

    public String getNameDetails() {
        return nameDetails;
    }

    public LocationJSON setNameDetails(String nameDetails) {
        this.nameDetails = nameDetails;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public LocationJSON setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getType() {
        return type;
    }

    public LocationJSON setType(String type) {
        this.type = type;
        return this;
    }

    public long getCapacity() {
        return capacity;
    }

    public LocationJSON setCapacity(long capacity) {
        this.capacity = capacity;
        return this;
    }

    public long getArea() {
        return area;
    }

    public LocationJSON setArea(long area) {
        this.area = area;
        return this;
    }

    public long getVolume() {
        return volume;
    }

    public LocationJSON setVolume(long volume) {
        this.volume = volume;
        return this;
    }

    public long getPorts() {
        return ports;
    }

    public LocationJSON setPorts(long ports) {
        this.ports = ports;
        return this;
    }

    public long getAccommodations() {
        return accommodations;
    }

    public LocationJSON setAccommodations(long accommodations) {
        this.accommodations = accommodations;
        return this;
    }
}
