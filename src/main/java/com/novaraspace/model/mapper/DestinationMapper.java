package com.novaraspace.model.mapper;

import com.novaraspace.model.dto.destination.DestinationDTO;
import com.novaraspace.model.entity.Destination;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = LocationMapper.class)
public abstract class DestinationMapper {

    public abstract DestinationDTO toDTO(Destination destination);
}
