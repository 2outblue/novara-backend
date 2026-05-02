package com.novaraspace.schedulers;

import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.audit.AuditScheduledTaskType;
import com.novaraspace.model.enums.audit.Outcome;
import com.novaraspace.model.events.ScheduledTaskEvent;
import com.novaraspace.repository.RefreshTokenRepository;
import com.novaraspace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
public class AuthCleanupScheduler {
    private final RefreshTokenRepository refreshTokenRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;

    @Value("${app.user.unverified-cleanup-days}")
    private int unverifiedUsersCleanupDaysBack;

    public AuthCleanupScheduler(RefreshTokenRepository refreshTokenRepository, ApplicationEventPublisher eventPublisher, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
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

    @Scheduled(cron = "0 0 4 * * WED")
    @Transactional
    public void cleanupUnverifiedAccounts() {
        Instant cleanupTime = Instant.now()
                .minusSeconds(unverifiedUsersCleanupDaysBack * 24L * 3600);
        int deletedUsers = userRepository
                .deleteAllByCreatedAtBeforeAndStatusIs(cleanupTime, AccountStatus.PENDING_ACTIVATION);

        eventPublisher.publishEvent(new ScheduledTaskEvent(
                AuditScheduledTaskType.UNVERIFIED_USER_CLEANUP,
                deletedUsers,
                Outcome.SUCCESS
        ));
    }
}
