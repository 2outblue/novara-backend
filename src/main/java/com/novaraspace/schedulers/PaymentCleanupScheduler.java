package com.novaraspace.schedulers;

import com.novaraspace.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentCleanupScheduler {

    private final PaymentRepository paymentRepository;
    @Value("${app.booking.cleanup-months-back}")
    private int cleanupMonthsBack;

    public PaymentCleanupScheduler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    //    @Scheduled(cron = "0 0 4 * * *")
//    @Scheduled(cron = "0 * * * * *")
    public void deleteOldPayments() {
        //Both join table deletion and actual record deletion should happen in one transaction ofcourse

        LocalDateTime twoYearsAgo = LocalDateTime.now().minusMonths(cleanupMonthsBack).minusMonths(2);

        paymentRepository.deleteUserPaymentsRowsOlderThan(twoYearsAgo);
        paymentRepository.deleteByCreatedAtBeforeAndBookingConfirmIsFalse(twoYearsAgo);
    }
}
