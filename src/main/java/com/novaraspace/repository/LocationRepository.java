package com.novaraspace.repository;

import com.novaraspace.model.entity.Location;
import com.novaraspace.model.enums.FlightRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByCode(String code);

    List<Location> findAllByRegion(FlightRegion region);
}
