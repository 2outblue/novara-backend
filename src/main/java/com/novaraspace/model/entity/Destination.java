package com.novaraspace.model.entity;

import com.novaraspace.model.embedded.DestinationDetails;
import com.novaraspace.model.embedded.DestinationPoint;
import com.novaraspace.model.enums.DestinationName;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Destination extends BaseEntity {
    private DestinationName name;
    @Column(nullable = false, length = 500)
    private String mainDesc;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "destinations_details", joinColumns = @JoinColumn(name = "destination_id"))
    @Size(max = 10)
    private List<DestinationDetails> primaryDets;
    @Column(nullable = false, length = 400)
    private String secondaryDesc;

    @OneToMany
    @JoinColumn(name = "destination_id")
    private List<Location> locations;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "destinations_points", joinColumns = @JoinColumn(name = "destination_id"))
    @Size(max = 7)
    private List<DestinationPoint> bodyPoints;

    public DestinationName getName() {
        return name;
    }

    public Destination setName(DestinationName name) {
        this.name = name;
        return this;
    }

    public String getMainDesc() {
        return mainDesc;
    }

    public Destination setMainDesc(String mainDesc) {
        this.mainDesc = mainDesc;
        return this;
    }

    public List<DestinationDetails> getPrimaryDets() {
        return primaryDets;
    }

    public Destination setPrimaryDets(List<DestinationDetails> primaryDets) {
        this.primaryDets = primaryDets;
        return this;
    }


    //    public DestinationDetails getPrimaryDets() {
//        return primaryDets;
//    }
//
//    public Destination setPrimaryDets(DestinationDetails primaryDets) {
//        this.primaryDets = primaryDets;
//        return this;
//    }

    public String getSecondaryDesc() {
        return secondaryDesc;
    }

    public Destination setSecondaryDesc(String secondaryDesc) {
        this.secondaryDesc = secondaryDesc;
        return this;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public Destination setLocations(List<Location> locations) {
        this.locations = locations;
        return this;
    }

    public @Size(max = 7) List<DestinationPoint> getBodyPoints() {
        return bodyPoints;
    }

    public Destination setBodyPoints(@Size(max = 7) List<DestinationPoint> bodyPoints) {
        this.bodyPoints = bodyPoints;
        return this;
    }
}
