package com.novaraspace.repository;

import com.novaraspace.model.domain.FlightReserveContext;
import com.novaraspace.model.entity.FlightInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightInstanceRepository extends JpaRepository<FlightInstance, Long> {

    Optional<FlightInstance> findByPublicId(String publicId);

    @Query("select distinct cast(fi.departureDate as localdate) from FlightInstance fi where fi.flightTemplate.id in :templateIds and cast(fi.departureDate as localdate) between :startDate and :endDate order by 1")
    List<LocalDate> findDistinctAvailabilityDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("templateIds") List<Long> templateIds
    );


    @Query("select fi from FlightInstance fi where fi.flightTemplate.id in :templateIds and cast(fi.departureDate as localdate) between :startDate and :endDate")
    List<FlightInstance> findAllWithTemplateIdsAndWithinRange(@Param("templateIds") List<Long> templateIds,
                                                                 @Param("startDate") LocalDate startDate,
                                                                 @Param("endDate") LocalDate endDate);


}
