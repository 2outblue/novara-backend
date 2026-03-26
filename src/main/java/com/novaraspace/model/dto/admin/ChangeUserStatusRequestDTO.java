package com.novaraspace.model.dto.admin;

import com.novaraspace.model.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ChangeUserStatusRequestDTO {
    @NotNull
    @Positive
    private Long id;
    @NotNull
    private AccountStatus status;

    public Long getId() {
        return id;
    }

    public ChangeUserStatusRequestDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public ChangeUserStatusRequestDTO setStatus(AccountStatus status) {
        this.status = status;
        return this;
    }
}
