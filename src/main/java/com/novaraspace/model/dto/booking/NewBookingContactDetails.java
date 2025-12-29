package com.novaraspace.model.dto.booking;

import com.novaraspace.validation.annotations.ValidCountryCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class NewBookingContactDetails {
    @NotEmpty
    @ValidCountryCode
    private String contactCountryCode;
    @NotEmpty
    @Size(min = 5, max = 15)
    private String contactMobile;
    @NotEmpty
    @Email
    private String contactEmail;

    public String getContactCountryCode() {
        return contactCountryCode;
    }

    public NewBookingContactDetails setContactCountryCode(String contactCountryCode) {
        this.contactCountryCode = contactCountryCode;
        return this;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public NewBookingContactDetails setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
        return this;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public NewBookingContactDetails setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }
}
