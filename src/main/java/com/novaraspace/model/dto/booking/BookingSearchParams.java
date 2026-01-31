package com.novaraspace.model.dto.booking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class BookingSearchParams {
    @NotBlank
    @Length(min = 6, max = 6)
    @Pattern(regexp = "^[A-Z][A-Z0-9]{2}\\d[A-Z0-9][A-Z]$")
    private String reference;
    @NotBlank
    private String lastName;

    public String getReference() {
        return reference;
    }

    public BookingSearchParams setReference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public BookingSearchParams setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
