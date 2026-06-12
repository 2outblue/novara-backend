package com.novaraspace.model.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;

public class UserDetailsRequestDTO {
    @Min(1)
    private Long id;
    @Email
    private String email;

    public Long getId() {
        return id;
    }

    public UserDetailsRequestDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDetailsRequestDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
