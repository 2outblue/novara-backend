package com.novaraspace.model.dto.admin;

import com.novaraspace.model.enums.AccountStatus;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDateTime;

public class UsersStatusRequestDTO {
    @NotNull
    private AccountStatus status;
    @NotNull
    @Past
    private LocalDateTime registeredAfter;


    @AssertTrue(message = "Invalid user outcome request - invalid date.")
    public boolean isRegisteredAfterDateValid() {
        return registeredAfter != null
                && registeredAfter.isAfter(LocalDateTime.now().minusMonths(1).minusHours(1));
    }

    public AccountStatus getStatus() {
        return status;
    }

    public UsersStatusRequestDTO setStatus(AccountStatus status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getRegisteredAfter() {
        return registeredAfter;
    }

    public UsersStatusRequestDTO setRegisteredAfter(LocalDateTime registeredAfter) {
        this.registeredAfter = registeredAfter;
        return this;
    }
}
