package com.novaraspace.repository;

import com.novaraspace.model.entity.FlightTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightTemplateRepository extends JpaRepository<FlightTemplate, Long> {
}
