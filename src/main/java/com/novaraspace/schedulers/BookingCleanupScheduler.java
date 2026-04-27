package com.novaraspace.schedulers;

import com.novaraspace.repository.BookingQuoteRepository;
import com.novaraspace.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class BookingCleanupScheduler {

    private final BookingRepository bookingRepository;
    private final BookingQuoteRepository bookingQuoteRepository;

    @Value("${app.booking.cleanup-months-back}")
    private int cleanupMonthsBack;

    public BookingCleanupScheduler(BookingRepository bookingRepository, BookingQuoteRepository bookingQuoteRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingQuoteRepository = bookingQuoteRepository;
    }

    //TODO: Delete only non-bookingConfirm payments - ie. this would be the change-flight
    // payments


    //Both join table deletion and actual record deletion should happen in one transaction ofcourse
    public void cleanUpOldBookings() {
        LocalDateTime twoYearsAgo = LocalDateTime.now().minusMonths(cleanupMonthsBack);

        bookingRepository.deleteUserBookingsRowsBefore(twoYearsAgo);
        bookingRepository.deleteAllByCreatedAtBefore(twoYearsAgo);
    }


    //    @Scheduled(cron = "0 0 4 * * *")
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void cleanUpExpiredBookingQuotes() {
        LocalDateTime now = LocalDateTime.now();
        bookingQuoteRepository.deleteAllByExpiresAtBefore(now);
    }
}
