package com.novaraspace.schedulers;

import com.novaraspace.repository.PasswordResetTokenRepository;
import com.novaraspace.repository.RefreshTokenRepository;
import com.novaraspace.repository.VerificationTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;

@Component
public class AuthCleanupScheduler {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    public AuthCleanupScheduler(PasswordResetTokenRepository passwordResetTokenRepository, RefreshTokenRepository refreshTokenRepository, VerificationTokenRepository verificationTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.verificationTokenRepository = verificationTokenRepository;
    }

//TODO: NEeed transactional

//    @Scheduled(cron = "0 0 4 * * *")
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void cleanupInvalidRefreshTokens() {
        Instant now = Instant.now();
        refreshTokenRepository.deleteAllByExpiryDateBeforeOrRevokedTrue(now);
    }


//    //    @Scheduled(cron = "0 0 4 * * *")
//    @Scheduled(cron = "0 * * * * *")
//    public void cleanupInvalidVerificationTokens() {
//        Instant now = Instant.now();
//        verificationTokenRepository.deleteAllByExpiresAtBeforeOrUsedTrue(now);
//    }
}
