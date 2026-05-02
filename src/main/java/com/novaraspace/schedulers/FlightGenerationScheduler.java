package com.novaraspace.schedulers;

import com.novaraspace.model.dto.flight.FlightInstanceGenerationParams;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.enums.audit.AuditScheduledTaskType;
import com.novaraspace.model.enums.audit.Outcome;
import com.novaraspace.model.events.ScheduledTaskEvent;
import com.novaraspace.repository.FlightInstanceRepository;
import com.novaraspace.service.FlightGenerationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class FlightGenerationScheduler {
    @Value("${app.flight.max-future-departure-days}")
    private int maxFutureDepartureDays;

    private final FlightInstanceRepository flightInstanceRepository;
    private final FlightGenerationService flightGenerationService;
    private final ApplicationEventPublisher eventPublisher;

    public FlightGenerationScheduler(FlightInstanceRepository flightInstanceRepository, FlightGenerationService flightGenerationService, ApplicationEventPublisher eventPublisher) {
        this.flightInstanceRepository = flightInstanceRepository;
        this.flightGenerationService = flightGenerationService;
        this.eventPublisher = eventPublisher;
    }


    @Scheduled(cron = "0 0 4 * * TUE")
    @Transactional
    public void generateFlights() {
        Optional<FlightInstance> flight = flightInstanceRepository.findTopByOrderByDepartureDateDesc();
        if (flight.isEmpty()) {
            return;
        }
        FlightInstance latestFlight = flight.get();
        LocalDateTime latestDepartureDate = latestFlight.getDepartureDate();

        LocalDate generationStartDate = latestDepartureDate.toLocalDate().plusDays(1);
        LocalDate generationEndDate = LocalDate.now().plusDays(maxFutureDepartureDays - 2);
        if (!generationEndDate.isAfter(generationStartDate)) { return; }

        FlightInstanceGenerationParams params = new FlightInstanceGenerationParams(
                generationStartDate, generationEndDate);

        int generatedInstances = flightGenerationService.generateForAllTemplates(params);
        eventPublisher.publishEvent(new ScheduledTaskEvent(
                AuditScheduledTaskType.FLIGHT_GENERATION,
                generatedInstances,
                Outcome.SUCCESS
        ));
    }
}
