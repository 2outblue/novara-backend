package com.novaraspace.model.other;

public class LocationJSON {
    private String location;
    private String name;
    private String longName;
    private String code;

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
}
