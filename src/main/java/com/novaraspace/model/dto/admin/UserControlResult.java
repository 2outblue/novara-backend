package com.novaraspace.model.dto.admin;

import com.novaraspace.model.enums.AccountStatus;

import java.time.Instant;

public class UserControlResult {
    private Long id;
    private String email;
    private Instant createdAt;
    private Instant lastLoginAt;
    private AccountStatus status;

    public Long getId() {
        return id;
    }

    public UserControlResult setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserControlResult setEmail(String email) {
        this.email = email;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UserControlResult setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    public UserControlResult setLastLoginAt(Instant lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        return this;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public UserControlResult setStatus(AccountStatus status) {
        this.status = status;
        return this;
    }
}
