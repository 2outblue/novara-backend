package com.novaraspace.model.dto.user;

import java.time.Instant;
import java.util.Date;

public class InitialAccountDataDTO {

    private String publicId;
    private Instant createdAt;
    private String firstName;
    private String lastName;
    private Date dob;
    private String email;
    private String countryCode;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String country;
    private boolean newsletter;
    private boolean marketing;

    public InitialAccountDataDTO() {
    }

    public String getPublicId() {
        return publicId;
    }

    public InitialAccountDataDTO setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public InitialAccountDataDTO setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public InitialAccountDataDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public InitialAccountDataDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Date getDob() {
        return dob;
    }

    public InitialAccountDataDTO setDob(Date dob) {
        this.dob = dob;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public InitialAccountDataDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public InitialAccountDataDTO setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public InitialAccountDataDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public InitialAccountDataDTO setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public InitialAccountDataDTO setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public InitialAccountDataDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public InitialAccountDataDTO setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
        return this;
    }

    public boolean isMarketing() {
        return marketing;
    }

    public InitialAccountDataDTO setMarketing(boolean marketing) {
        this.marketing = marketing;
        return this;
    }
}
