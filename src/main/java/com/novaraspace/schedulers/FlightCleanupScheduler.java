package com.novaraspace.schedulers;

import com.novaraspace.model.enums.audit.AuditScheduledTaskType;
import com.novaraspace.model.enums.audit.Outcome;
import com.novaraspace.model.events.ScheduledTaskEvent;
import com.novaraspace.repository.FlightInstanceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class FlightCleanupScheduler {
    private final FlightInstanceRepository flightInstanceRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${app.flight.unbooked-cleanup-days-back}")
    private int unbookedFlightsCleanupDaysBack;

    @Value("${app.booking.cleanup-months-back}")
    private int bookingCleanupMonthsBack;

    public FlightCleanupScheduler(FlightInstanceRepository flightInstanceRepository, ApplicationEventPublisher eventPublisher) {
        this.flightInstanceRepository = flightInstanceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Scheduled(cron = "0 0 4 * * MON")
    @CacheEvict(value = "routeAvailability", allEntries = true)
    public void cleanUpNonBookedFlights() {
        LocalDateTime cleanupDate = LocalDateTime.now().minusDays(unbookedFlightsCleanupDaysBack);
        int deletedFlights = flightInstanceRepository.deleteUnbookedFlightsDepartingBefore(cleanupDate);
        eventPublisher.publishEvent(new ScheduledTaskEvent(
                AuditScheduledTaskType.FLIGHT_CLEANUP,
                deletedFlights,
                Outcome.SUCCESS
        ));
    }

    @Transactional
    @Scheduled(cron = "0 0 4 3 * ?")
    @CacheEvict(value = "routeAvailability", allEntries = true)
    public void cleanUpBookedFlights() {
        //Delete booked flights which won't have FK constraint violation as the bookings
        // with which they are associated were already deleted.
        LocalDateTime oldestExistingBookingDate = LocalDateTime.now().minusMonths(bookingCleanupMonthsBack);
        LocalDateTime safeDateForFlightCleanup = oldestExistingBookingDate.minusMonths(1);

        int deletedFlights = flightInstanceRepository.deleteAllByArrivalDateBefore(safeDateForFlightCleanup);
        eventPublisher.publishEvent(new ScheduledTaskEvent(
                AuditScheduledTaskType.FLIGHT_CLEANUP,
                deletedFlights,
                Outcome.SUCCESS
        ));
    }

}
