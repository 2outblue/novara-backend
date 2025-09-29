package com.novaraspace.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.Instant;
import java.util.UUID;

import static java.sql.Types.VARCHAR;

@Entity
public class RefreshToken extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String publicKey;
    @Column(nullable = false, unique = true)
    private String token;
    @Column(nullable = false)
    @JdbcTypeCode(VARCHAR)
    private UUID userAuthId;
    @Column(nullable = false)
    private Instant expiryDate;
    @Column(nullable = false)
    @JdbcTypeCode(VARCHAR)
    private UUID familyId;
    @Column(length = 45)
    private String ipAddress;
    @Column(nullable = false)
    private boolean revoked = false;

    public RefreshToken() {
    }

    public RefreshToken(String publicKey, String token, UUID userAuthId, Instant expiryDate, UUID familyId) {
        this.publicKey = publicKey;
        this.token = token;
        this.userAuthId = userAuthId;
        this.expiryDate = expiryDate;
        this.familyId = familyId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public RefreshToken setPublicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public String getToken() {
        return token;
    }

    public RefreshToken setToken(String token) {
        this.token = token;
        return this;
    }

    public UUID getUserAuthId() {
        return userAuthId;
    }

    public RefreshToken setUserAuthId(UUID userAuthId) {
        this.userAuthId = userAuthId;
        return this;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public RefreshToken setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public UUID getFamilyId() {
        return familyId;
    }

    public RefreshToken setFamilyId(UUID familyId) {
        this.familyId = familyId;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public RefreshToken setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public RefreshToken setRevoked(boolean revoked) {
        this.revoked = revoked;
        return this;
    }
}
