package com.novaraspace.repository;

import com.novaraspace.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_payments WHERE payment_id IN (SELECT id FROM payment WHERE booking_confirm = false AND created_at < :date)", nativeQuery = true)
    void deleteUserPaymentsRowsOlderThan(LocalDateTime date);

    void deleteByCreatedAtBeforeAndBookingConfirmIsFalse(LocalDateTime before);

    Long countByServiceReference(String serviceReference);
}
