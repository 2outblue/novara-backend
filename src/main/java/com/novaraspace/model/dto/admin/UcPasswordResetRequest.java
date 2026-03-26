package com.novaraspace.model.dto.admin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UcPasswordResetRequest {
    @NotNull
    @Positive
    private Long id;

    public Long getId() {
        return id;
    }

    public UcPasswordResetRequest setId(Long id) {
        this.id = id;
        return this;
    }
}
