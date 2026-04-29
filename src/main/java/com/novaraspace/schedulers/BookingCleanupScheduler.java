package com.novaraspace.schedulers;

import com.novaraspace.model.enums.audit.AuditScheduledTaskType;
import com.novaraspace.model.enums.audit.Outcome;
import com.novaraspace.model.events.ScheduledTaskEvent;
import com.novaraspace.repository.BookingQuoteRepository;
import com.novaraspace.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class BookingCleanupScheduler {

    private final BookingRepository bookingRepository;
    private final BookingQuoteRepository bookingQuoteRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${app.booking.cleanup-months-back}")
    private int cleanupMonthsBack;

    public BookingCleanupScheduler(BookingRepository bookingRepository, BookingQuoteRepository bookingQuoteRepository, ApplicationEventPublisher eventPublisher) {
        this.bookingRepository = bookingRepository;
        this.bookingQuoteRepository = bookingQuoteRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Scheduled(cron = "0 0 4 1 * ?")
    public void cleanUpOldBookings() {
        LocalDateTime cleanUpDate = LocalDateTime.now().minusMonths(cleanupMonthsBack);

        bookingRepository.deleteUserBookingsRowsBefore(cleanUpDate);
        int deletedBookings = bookingRepository.deleteAllByCreatedAtBefore(cleanUpDate);

        eventPublisher.publishEvent(new ScheduledTaskEvent(
                AuditScheduledTaskType.BOOKING_CLEANUP,
                deletedBookings,
                Outcome.SUCCESS
        ));
    }

    @Transactional
    @Scheduled(cron = "0 0 5 * * *")
    public void cleanUpExpiredBookingQuotes() {
        LocalDateTime now = LocalDateTime.now();
        int deletedQuotes = bookingQuoteRepository.deleteAllByExpiresAtBefore(now);

        eventPublisher.publishEvent(new ScheduledTaskEvent(
                AuditScheduledTaskType.BOOKING_QUOTE_CLEANUP,
                deletedQuotes,
                Outcome.SUCCESS
        ));
    }
}
