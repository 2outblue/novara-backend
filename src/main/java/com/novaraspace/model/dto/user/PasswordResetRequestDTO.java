package com.novaraspace.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordResetRequestDTO {
    @NotBlank
    @Size(min = 5, max = 64)
    private String newPassword;
    @NotBlank
    @Size(min = 40, max = 60)
    private String resetToken;

    public String getNewPassword() {
        return newPassword;
    }

    public PasswordResetRequestDTO setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getResetToken() {
        return resetToken;
    }

    public PasswordResetRequestDTO setResetToken(String resetToken) {
        this.resetToken = resetToken;
        return this;
    }
}
