package com.novaraspace.model.entity;

import com.novaraspace.model.enums.CabinClassEnum;
import com.novaraspace.model.enums.FlightRegion;
import com.novaraspace.model.enums.VehicleAmenity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    private Long id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "vehicle_amenities", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<VehicleAmenity> amenities;

    @JoinColumn(name = "first_class")
    @ManyToOne(fetch = FetchType.EAGER)
    private CabinClass firstClass;
    @JoinColumn(name = "middle_class")
    @ManyToOne(fetch = FetchType.EAGER)
    private CabinClass middleClass;
    @JoinColumn(name = "lower_class")
    @ManyToOne(fetch = FetchType.EAGER)
    private CabinClass lowerClass;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "vehicle_regions", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<FlightRegion> supportedRegions;


    public int getTotalSeats() {
        return firstClass.getTotalSeats()
                + middleClass.getTotalSeats()
                + lowerClass.getTotalSeats();
    }

    public CabinClass getClassByEnum(CabinClassEnum cabinEnum) {
        return switch (cabinEnum) {
            case FIRST -> getFirstClass();
            case MIDDLE -> getMiddleClass();
            case LOWER -> getLowerClass();
        };
    }

    public Long getId() {
        return id;
    }

    public Vehicle setId(Long id) {
        this.id = id;
        return this;
    }

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
