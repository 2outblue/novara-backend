package com.novaraspace.service;

import com.novaraspace.model.entity.User;
import com.novaraspace.model.entity.VerificationToken;
import com.novaraspace.model.exception.FailedOperationException;
import com.novaraspace.model.exception.VerificationTokenException;
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
        if (linkOrCode.length() <= 7 && linkOrCode.matches("/\\d+/")) {
            return verificationTokenRepository.findByCode(linkOrCode).orElseThrow(() -> new VerificationTokenException("Invalid verification code"));
        }
        return verificationTokenRepository.findByLinkToken(linkOrCode).orElseThrow(() -> new VerificationTokenException("Invalid verification code"));
    }


    public VerificationToken generateVerificationToken(User user) { //TODO: Maybe hash these with the passwordEncoder ?
        VerificationToken verificationToken = new VerificationToken();
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        String linkToken = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        String code = generateVerificationCode();

        return verificationToken
                .setLinkToken(linkToken)
                .setCode(code)
                .setUser(user)
                .setCreatedAt(Instant.now())
                .setExpiresAt(Instant.now().plusSeconds(verificationTokenExpiryHours * 60 * 60))
                .setUsed(false);
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
