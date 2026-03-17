package com.novaraspace.model.entity;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class PasswordResetToken extends BaseEntity {
    private String tokenValue;
    private LocalDateTime expiresOn;
    private String userAuthId;

    public String getTokenValue() {
        return tokenValue;
    }

    public PasswordResetToken setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
        return this;
    }

    public LocalDateTime getExpiresOn() {
        return expiresOn;
    }

    public PasswordResetToken setExpiresOn(LocalDateTime expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }

    public String getUserAuthId() {
        return userAuthId;
    }

    public PasswordResetToken setUserAuthId(String userAuthId) {
        this.userAuthId = userAuthId;
        return this;
    }
}
