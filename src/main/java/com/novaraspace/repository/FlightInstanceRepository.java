package com.novaraspace.repository;

import com.novaraspace.model.domain.FlightsWithinRangeRequest;
import com.novaraspace.model.dto.flight.FlightsScheduleFetchParams;
import com.novaraspace.model.entity.FlightInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("select fi from FlightInstance fi where fi.flightTemplate.id in :#{#req.templateIds} and cast(fi.departureDate as localdate) between :#{#req.startDate} and :#{#req.endDate}")
    List<FlightInstance> findAllForWithinRangeRequest(@Param("req") FlightsWithinRangeRequest req);

    @Query("""
    select fi from FlightInstance fi where fi.flightTemplate.id in :#{#params.templates}
    and cast(fi.departureDate as localdate) >= :#{#params.departureDate}
    and cast(fi.departureDate as localdate) <= :#{#params.departureDateMax}
""")
    Page<FlightInstance> getFlightsForSchedule(@Param("params")FlightsScheduleFetchParams params, Pageable pageable);


}
