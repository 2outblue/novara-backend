package com.novaraspace.repository;

import com.novaraspace.model.entity.FlightTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightTemplateRepository extends JpaRepository<FlightTemplate, Long> {


    @Query("select ft from FlightTemplate ft where ft.departureLocation.id = :depLocationId and ft.arrivalLocation.id = :arrLocationId")
    List<FlightTemplate> findAllByDepartureAndArrivalLocationIds(@Param("depLocationId") Long depLocationId, @Param("arrLocationId") Long arrLocationId);
}
