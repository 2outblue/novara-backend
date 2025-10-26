package com.novaraspace.repository;

import com.novaraspace.model.entity.CabinClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabinClassRepository extends JpaRepository<CabinClass, Long> {
}
