package com.novaraspace.model.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PasswordChangeRequestDTO {
    @NotEmpty
    @Size(min = 5, max = 240)
    private String oldPassword;
    @NotEmpty
    @Size(min = 5, max = 240)
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public PasswordChangeRequestDTO setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public PasswordChangeRequestDTO setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }
}
