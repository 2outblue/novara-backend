package com.novaraspace.model.other;

import java.util.List;

public class VehicleJSON {
    private String code;
    private String name;

    private List<String> amenities;

//    private boolean eva;
//    private boolean observationLounge;
//    private boolean vr;
//    private boolean galley;

    private Long firstClassId;
    private Long middleClassId;
    private Long lowerClassId;

    private List<String> supportedRegions;

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

    //    public boolean isEva() {
//        return eva;
//    }
//
//    public VehicleJSON setEva(boolean eva) {
//        this.eva = eva;
//        return this;
//    }
//
//    public boolean isObservationLounge() {
//        return observationLounge;
//    }
//
//    public VehicleJSON setObservationLounge(boolean observationLounge) {
//        this.observationLounge = observationLounge;
//        return this;
//    }
//
//    public boolean isVr() {
//        return vr;
//    }
//
//    public VehicleJSON setVr(boolean vr) {
//        this.vr = vr;
//        return this;
//    }
//
//    public boolean isGalley() {
//        return galley;
//    }
//
//    public VehicleJSON setGalley(boolean galley) {
//        this.galley = galley;
//        return this;
//    }

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
