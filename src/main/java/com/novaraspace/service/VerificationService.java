package com.novaraspace.service;

import com.novaraspace.model.entity.VerificationToken;
import com.novaraspace.model.exception.FailedOperationException;
import com.novaraspace.model.exception.VerificationException;
import com.novaraspace.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;

@Service
public class VerificationService {

    private final VerificationTokenRepository verificationTokenRepository;

    @Value("${app.verification-token-expiry-hours}")
    private long verificationTokenExpiryHours;

    public VerificationService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public VerificationToken getEntityByLinkTokenOrCode(String linkOrCode) {
        if (linkOrCode.length() <= 7 && linkOrCode.matches("\\d+")) {
            return verificationTokenRepository.findByCode(linkOrCode).orElse(null);
        }
        return verificationTokenRepository.findByLinkToken(linkOrCode).orElse(null);
    }

    public VerificationToken generateTokenForUserRegister(String email) {
        deleteAllExistingTokensForEmail(email);

        String linkToken = generateLinkToken();
        String code = generateVerificationCode();
        return new VerificationToken()
                .setLinkToken(linkToken)
                .setCode(code)
                .setUserEmail(email)
                .setCreatedAt(Instant.now())
                .setExpiresAt(Instant.now().plusSeconds(verificationTokenExpiryHours * 60 * 60))
                .setUsed(false);
    }

    public VerificationToken generateTokenForRetryActivation(VerificationToken existingToken) {
        String linkToken = generateLinkToken();
        String code = generateVerificationCode();
        return new VerificationToken()
                .setLinkToken(linkToken)
                .setCode(code)
                .setUserEmail(existingToken.getUserEmail())
                .setCreatedAt(Instant.now())
                .setExpiresAt(Instant.now().plusSeconds(verificationTokenExpiryHours * 60 * 60))
                .setUsed(false)
                .setSerialNumber(existingToken.getSerialNumber() + 1);
    }

    public void deleteAllExistingTokensForEmail(String email) {
        verificationTokenRepository.deleteAllByUserEmail(email);
        verificationTokenRepository.flush();
    }

    private String generateLinkToken() {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }


    private String generateVerificationCode() { // This has to be with length <= 7
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 5; i++) {
            String code = Integer.toString(random.nextInt(900000) + 100000);
            if (!verificationTokenRepository.existsByCode(code)) {
                return code;
            }
        }
        throw new FailedOperationException();
    }
}
