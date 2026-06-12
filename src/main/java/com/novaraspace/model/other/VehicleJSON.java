package com.novaraspace.model.other;

import java.util.List;

public class VehicleJSON {
    private long id;
    private String code;
    private String name;

    private List<String> amenities;

    private Long firstClassId;
    private Long middleClassId;
    private Long lowerClassId;

    private List<String> supportedRegions;

    public long getId() {
        return id;
    }

    public VehicleJSON setId(long id) {
        this.id = id;
        return this;
    }

    public java.lang.String getCode() {
        return code;
    }

    public VehicleJSON setCode(String code) {
        this.code = code;
        return this;
    }

    public java.lang.String getName() {
        return name;
    }

    public VehicleJSON setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public VehicleJSON setAmenities(List<String> amenities) {
        this.amenities = amenities;
        return this;
    }


    public Long getFirstClassId() {
        return firstClassId;
    }

    public VehicleJSON setFirstClassId(Long firstClassId) {
        this.firstClassId = firstClassId;
        return this;
    }

    public Long getMiddleClassId() {
        return middleClassId;
    }

    public VehicleJSON setMiddleClassId(Long middleClassId) {
        this.middleClassId = middleClassId;
        return this;
    }

    public Long getLowerClassId() {
        return lowerClassId;
    }

    public VehicleJSON setLowerClassId(Long lowerClassId) {
        this.lowerClassId = lowerClassId;
        return this;
    }

    public List<String> getSupportedRegions() {
        return supportedRegions;
    }

    public VehicleJSON setSupportedRegions(List<String> supportedRegions) {
        this.supportedRegions = supportedRegions;
        return this;
    }
}
