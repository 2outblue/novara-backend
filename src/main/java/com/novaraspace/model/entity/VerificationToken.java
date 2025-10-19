package com.novaraspace.model.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class VerificationToken extends BaseEntity {

    @Column(nullable = false)
    private String linkToken;
    @Column(nullable = false)
    private String code;

//    @OneToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private Instant expiresAt;
    @Column(nullable = false)
    private boolean used;

    public String getLinkToken() {
        return linkToken;
    }

    public VerificationToken setLinkToken(String linkToken) {
        this.linkToken = linkToken;
        return this;
    }

    public String getCode() {
        return code;
    }

    public VerificationToken setCode(String code) {
        this.code = code;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public VerificationToken setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    //    public User getUser() {
//        return user;
//    }
//
//    public VerificationToken setUser(User user) {
//        this.user = user;
//        return this;
//    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public VerificationToken setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public VerificationToken setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public boolean isUsed() {
        return used;
    }

    public VerificationToken setUsed(boolean used) {
        this.used = used;
        return this;
    }
}
