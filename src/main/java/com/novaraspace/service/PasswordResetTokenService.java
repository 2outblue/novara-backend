package com.novaraspace.service;

import com.novaraspace.model.entity.PasswordResetToken;
import com.novaraspace.model.exception.UserException;
import com.novaraspace.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository repository;

    public PasswordResetTokenService(PasswordResetTokenRepository repository) {
        this.repository = repository;
    }

    //TODO: Encode the token value in the DB
    public PasswordResetToken generateNewToken(String authId) {
        repository.deleteAllByUserAuthId(authId);
        PasswordResetToken token = new PasswordResetToken()
                .setTokenValue(generateTokenValue())
                .setUserAuthId(authId)
                .setExpiresOn(LocalDateTime.now().plusMinutes(30));
        return repository.save(token);
    }

    public Optional<PasswordResetToken> findAndDeleteTokenByValue(String tokenValue) {
        Optional<PasswordResetToken> token = repository.findByTokenValue(tokenValue);
        Integer del = repository.deleteByTokenValue(tokenValue);
        if ((int) del <= 0 && token.isPresent()) {
            throw UserException.updateFailed();
        }
        return token;
    }

    private String generateTokenValue() {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
