package com.novaraspace.model.dto.destination;

import com.novaraspace.model.dto.location.LocationDetailsDTO;
import com.novaraspace.model.embedded.DestinationDetails;
import com.novaraspace.model.embedded.DestinationPoint;
import com.novaraspace.model.entity.Location;
import com.novaraspace.model.enums.DestinationName;

import java.util.List;

public class DestinationDTO {
    private DestinationName name;
    private String mainDesc;
    private List<DestinationDetails> primaryDets;
    private String secondaryDesc;
    private List<LocationDetailsDTO> locations;
    private List<DestinationPoint> bodyPoints;

    public DestinationName getName() {
        return name;
    }

    public DestinationDTO setName(DestinationName name) {
        this.name = name;
        return this;
    }

    public String getMainDesc() {
        return mainDesc;
    }

    public DestinationDTO setMainDesc(String mainDesc) {
        this.mainDesc = mainDesc;
        return this;
    }

    public List<DestinationDetails> getPrimaryDets() {
        return primaryDets;
    }

    public DestinationDTO setPrimaryDets(List<DestinationDetails> primaryDets) {
        this.primaryDets = primaryDets;
        return this;
    }

    public String getSecondaryDesc() {
        return secondaryDesc;
    }

    public DestinationDTO setSecondaryDesc(String secondaryDesc) {
        this.secondaryDesc = secondaryDesc;
        return this;
    }

    public List<LocationDetailsDTO> getLocations() {
        return locations;
    }

    public DestinationDTO setLocations(List<LocationDetailsDTO> locations) {
        this.locations = locations;
        return this;
    }

    public List<DestinationPoint> getBodyPoints() {
        return bodyPoints;
    }

    public DestinationDTO setBodyPoints(List<DestinationPoint> bodyPoints) {
        this.bodyPoints = bodyPoints;
        return this;
    }
}
