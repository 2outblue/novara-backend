package com.novaraspace.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDTO {
    @NotBlank
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public EmailDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
