package com.novaraspace.model.mapper;

import com.novaraspace.model.dto.location.LocationDTO;
import com.novaraspace.model.entity.Location;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class LocationMapper {

    public abstract LocationDTO locationToLocationDTO(Location location);
}
