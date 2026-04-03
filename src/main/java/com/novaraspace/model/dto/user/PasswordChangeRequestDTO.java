package com.novaraspace.model.dto.user;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PasswordChangeRequestDTO {
    @NotBlank
    @Size(min = 5, max = 64)
    private String oldPassword;
    @NotBlank
    @Size(min = 5, max = 64)
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public PasswordChangeRequestDTO setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    @AssertFalse(message = "Passwords must not match.")
    public boolean passwordMatch() {
        if (newPassword == null || oldPassword == null) { return false; }
        return newPassword.equals(oldPassword);
    }

    public String getNewPassword() {
        return newPassword;
    }

    public PasswordChangeRequestDTO setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }
}
