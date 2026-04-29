package com.novaraspace.schedulers;

import com.novaraspace.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class PaymentCleanupScheduler {
    private final PaymentRepository paymentRepository;
    @Value("${app.booking.cleanup-months-back}")
    private int bookingCleanupMonthsBack;

    public PaymentCleanupScheduler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 4 3 * ?")
    public void deleteOldPayments() {
        LocalDateTime cleanUpDate = LocalDateTime.now().minusMonths(bookingCleanupMonthsBack).minusMonths(2);

        paymentRepository.deleteUserPaymentsRowsOlderThan(cleanUpDate);
        paymentRepository.deleteByCreatedAtBeforeAndBookingConfirmIsFalse(cleanUpDate);
    }
}
