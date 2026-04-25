package com.novaraspace.repository;

import com.novaraspace.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_payments WHERE payment_id = :id", nativeQuery = true)
    void deleteJoinTableRowsByReference(Long id);
//    IN (SELECT id FROM payments WHERE reference = :ref)
}
