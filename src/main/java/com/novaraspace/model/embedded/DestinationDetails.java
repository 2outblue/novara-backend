package com.novaraspace.model.embedded;

import jakarta.persistence.Embeddable;

@Embeddable
public class DestinationDetails {

    private String detailName;
    private String detailValue;

    public String getDetailName() {
        return detailName;
    }

    public DestinationDetails setDetailName(String detailName) {
        this.detailName = detailName;
        return this;
    }

    public String getDetailValue() {
        return detailValue;
    }

    public DestinationDetails setDetailValue(String detailValue) {
        this.detailValue = detailValue;
        return this;
    }

    //    private String au;
//    private String gravitation;
//    private String radius;
//    private String orbitalPeriod;
//    private String orbitalSpeed;

//    public String getAu() {
//        return au;
//    }
//
//    public DestinationDetails setAu(String au) {
//        this.au = au;
//        return this;
//    }
//
//    public String getGravitation() {
//        return gravitation;
//    }
//
//    public DestinationDetails setGravitation(String gravitation) {
//        this.gravitation = gravitation;
//        return this;
//    }
//
//    public String getRadius() {
//        return radius;
//    }
//
//    public DestinationDetails setRadius(String radius) {
//        this.radius = radius;
//        return this;
//    }
//
//    public String getOrbitalPeriod() {
//        return orbitalPeriod;
//    }
//
//    public DestinationDetails setOrbitalPeriod(String orbitalPeriod) {
//        this.orbitalPeriod = orbitalPeriod;
//        return this;
//    }
//
//    public String getOrbitalSpeed() {
//        return orbitalSpeed;
//    }
//
//    public DestinationDetails setOrbitalSpeed(String orbitalSpeed) {
//        this.orbitalSpeed = orbitalSpeed;
//        return this;
//    }
}
