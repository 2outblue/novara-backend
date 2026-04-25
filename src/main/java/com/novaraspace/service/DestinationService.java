package com.novaraspace.service;

import com.novaraspace.model.dto.destination.DestinationDTO;
import com.novaraspace.model.entity.Destination;
import com.novaraspace.model.mapper.DestinationMapper;
import com.novaraspace.repository.DestinationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final DestinationMapper destinationMapper;

    public DestinationService(DestinationRepository destinationRepository, DestinationMapper destinationMapper) {
        this.destinationRepository = destinationRepository;
        this.destinationMapper = destinationMapper;
    }

    //TODO: Cache this
    @Transactional
    public List<DestinationDTO> getAllDestinations() {
        return destinationRepository.findAll().stream()
                .map(destinationMapper::toDTO).toList();
    }
}
