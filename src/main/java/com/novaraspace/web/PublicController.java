package com.novaraspace.web;

import com.novaraspace.model.dto.location.LocationGroupDTO;
import com.novaraspace.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    private final LocationService locationService;

    public PublicController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/location")
    public ResponseEntity<List<LocationGroupDTO>> getAllLocationGroups() {
        List<LocationGroupDTO> locationGroups = locationService.getAllLocationGroups();
        return ResponseEntity.ok(locationGroups);
    }
}
