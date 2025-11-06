package com.novaraspace.repository;

import com.novaraspace.model.entity.FlightInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightInstanceRepository extends JpaRepository<FlightInstance, Long> {


    @Query("select distinct cast(fi.departureDate as localdate) from FlightInstance fi where fi.flightTemplate.id in :templateIds and cast(fi.departureDate as localdate) between :startDate and :endDate order by 1")
    List<LocalDate> findDistinctAvailabilityDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("templateIds") List<Long> templateIds
    );
}
