package com.novaraspace.service;

import com.novaraspace.model.dto.location.LocationDTO;
import com.novaraspace.model.dto.location.LocationGroupDTO;
import com.novaraspace.model.entity.Location;
import com.novaraspace.model.enums.FlightRegion;
import com.novaraspace.model.exception.FailedOperationException;
import com.novaraspace.model.mapper.LocationMapper;
import com.novaraspace.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }



    public List<LocationGroupDTO> getAllLocationGroups() {
        List<Location> locations = locationRepository.findAll();
        if (locations.isEmpty()) {throw new FailedOperationException();}

        Map<FlightRegion, LocationGroupDTO> locationGroups = new HashMap<>();
        for (Location location : locations) {
            LocationDTO dto = locationMapper.locationToLocationDTO(location);

            if (locationGroups.containsKey(location.getRegion())) {
                locationGroups.get(location.getRegion()).getLocations().add(dto);
            } else {
                LocationGroupDTO locationGroupDTO = new LocationGroupDTO()
                        .setRegionCode(location.getRegion())
                        .setRegionLabel(location.getRegion().getLabel())
                        .setLocations(new ArrayList<>());
                locationGroupDTO.getLocations().add(dto);
                locationGroups.put(location.getRegion(), locationGroupDTO);
            }
        }

        return locationGroups
                .values()
                .stream()
                .toList();
    }
}
