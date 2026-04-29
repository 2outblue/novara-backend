package com.novaraspace.schedulers;

import com.novaraspace.model.enums.audit.AuditScheduledTaskType;
import com.novaraspace.model.enums.audit.Outcome;
import com.novaraspace.model.events.ScheduledTaskEvent;
import com.novaraspace.repository.RefreshTokenRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
public class AuthCleanupScheduler {
    private final RefreshTokenRepository refreshTokenRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AuthCleanupScheduler(RefreshTokenRepository refreshTokenRepository, ApplicationEventPublisher eventPublisher) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void cleanupInvalidRefreshTokens() {
        Instant now = Instant.now();
        int deletedTokens = refreshTokenRepository.deleteAllByExpiryDateBeforeOrRevokedTrue(now);

        eventPublisher.publishEvent(new ScheduledTaskEvent(
                AuditScheduledTaskType.REFRESH_TOKEN_CLEANUP,
                deletedTokens,
                Outcome.SUCCESS
        ));
    }
}
