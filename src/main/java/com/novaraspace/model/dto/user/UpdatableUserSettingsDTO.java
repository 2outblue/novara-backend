package com.novaraspace.model.dto.user;

import com.novaraspace.validation.annotations.ValidCountry;
import com.novaraspace.validation.annotations.ValidCountryCode;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdatableUserSettingsDTO {
    @ValidCountry
    private String country;
    @ValidCountryCode
    private String countryCode;
    @Size(min = 5, max = 15)
    @Pattern(regexp = "^\\d+$", message = "Must contain only digits")
    private String phoneNumber;
    @Size(min = 4, max = 70)
    private String addressLine1;
    @Size(min = 4, max = 70)
    private String addressLine2;
    private Boolean newsletter;
    private Boolean marketing;


    public boolean hasAnyNonNullFields() {
        return country != null || countryCode != null
                || phoneNumber != null || addressLine1 != null
                || addressLine2 != null || newsletter != null
                || marketing != null;
    }

    public String getCountry() {
        return country;
    }

    public UpdatableUserSettingsDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public UpdatableUserSettingsDTO setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UpdatableUserSettingsDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public UpdatableUserSettingsDTO setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public UpdatableUserSettingsDTO setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public UpdatableUserSettingsDTO setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
        return this;
    }

    public Boolean getMarketing() {
        return marketing;
    }

    public UpdatableUserSettingsDTO setMarketing(Boolean marketing) {
        this.marketing = marketing;
        return this;
    }
}
