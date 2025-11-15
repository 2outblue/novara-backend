package com.novaraspace.model.entity;

import com.novaraspace.model.enums.FlightRegion;
import com.novaraspace.model.enums.VehicleAmenity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "vehicle")
public class Vehicle extends BaseEntity {
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
//    @Column(nullable = false)
//    private boolean eva;
//    @Column(nullable = false)
//    private boolean observationLounge;
//    @Column(nullable = false)
//    private boolean vr;
//    @Column(nullable = false)
//    private boolean galley;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "vehicle_amenities", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<VehicleAmenity> amenities;

    @JoinColumn(name = "first_class")
    @ManyToOne
    private CabinClass firstClass;
    @JoinColumn(name = "middle_class")
    @ManyToOne
    private CabinClass middleClass;
    @JoinColumn(name = "lower_class")
    @ManyToOne
    private CabinClass lowerClass;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "vehicle_regions", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<FlightRegion> supportedRegions;



    public String getCode() {
        return code;
    }

    public Vehicle setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Vehicle setName(String name) {
        this.name = name;
        return this;
    }

    public Set<VehicleAmenity> getAmenities() {
        return amenities;
    }

    public Vehicle setAmenities(Set<VehicleAmenity> amenities) {
        this.amenities = amenities;
        return this;
    }

    //    public boolean hasEva() {
//        return eva;
//    }
//
//    public Vehicle setEva(boolean eva) {
//        this.eva = eva;
//        return this;
//    }
//
//    public boolean hasObservationLounge() {
//        return observationLounge;
//    }
//
//    public Vehicle setObservationLounge(boolean observationLounge) {
//        this.observationLounge = observationLounge;
//        return this;
//    }
//
//    public boolean hasVr() {
//        return vr;
//    }
//
//    public Vehicle setVr(boolean vr) {
//        this.vr = vr;
//        return this;
//    }
//
//    public boolean hasGalley() {
//        return galley;
//    }
//
//    public Vehicle setGalley(boolean galley) {
//        this.galley = galley;
//        return this;
//    }

    public CabinClass getFirstClass() {
        return firstClass;
    }

    public Vehicle setFirstClass(CabinClass firstClass) {
        this.firstClass = firstClass;
        return this;
    }

    public CabinClass getMiddleClass() {
        return middleClass;
    }

    public Vehicle setMiddleClass(CabinClass middleClass) {
        this.middleClass = middleClass;
        return this;
    }

    public CabinClass getLowerClass() {
        return lowerClass;
    }

    public Vehicle setLowerClass(CabinClass lowerClass) {
        this.lowerClass = lowerClass;
        return this;
    }

    public Set<FlightRegion> getSupportedRegions() {
        return supportedRegions;
    }

    public Vehicle setSupportedRegions(Set<FlightRegion> supportedRegions) {
        this.supportedRegions = supportedRegions;
        return this;
    }
}
