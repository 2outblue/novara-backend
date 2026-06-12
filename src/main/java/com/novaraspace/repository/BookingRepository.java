package com.novaraspace.repository;

import com.novaraspace.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByReference(String reference);

    Optional<Booking> findByReference(String reference);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users_bookings WHERE booking_id IN (SELECT id FROM booking WHERE created_at < :date AND demo = false)", nativeQuery = true)
    void deleteUserBookingsRowsBefore(LocalDateTime date);

    @Transactional
    Integer deleteAllByDemoIsFalseAndCreatedAtBefore(LocalDateTime date);
}
