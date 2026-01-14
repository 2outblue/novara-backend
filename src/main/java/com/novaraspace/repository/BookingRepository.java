package com.novaraspace.repository;

import com.novaraspace.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByReference(String reference);
}
