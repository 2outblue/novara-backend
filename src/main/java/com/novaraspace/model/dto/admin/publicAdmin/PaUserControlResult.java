package com.novaraspace.model.dto.admin.publicAdmin;

import com.novaraspace.model.enums.AccountStatus;

import java.time.Instant;

public class PaUserControlResult {
    private Long id;
    private String email;
    private Instant createdAt;
    private Instant lastLoginAt;
    private AccountStatus status;

    public Long getId() {
        return id;
    }

    public PaUserControlResult setId(Long id) {
        this.id = 0L;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PaUserControlResult setEmail(String email) {
        this.email = "****";
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public PaUserControlResult setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    public PaUserControlResult setLastLoginAt(Instant lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        return this;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public PaUserControlResult setStatus(AccountStatus status) {
        this.status = status;
        return this;
    }
}
