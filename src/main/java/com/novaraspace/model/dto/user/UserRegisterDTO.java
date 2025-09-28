package com.novaraspace.model.dto.user;

import com.novaraspace.validation.annotations.ValidCountry;
import com.novaraspace.validation.annotations.ValidCountryCode;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.UUID;

public class UserRegisterDTO {

    @NotEmpty
    private UUID authId;
    @NotEmpty
    @Size(min = 2, max = 60)
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 60)
    private String lastName;
    @NotNull
    @Past
    private Date dob;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 5, max = 60)
    private String password;
    @NotEmpty
    @ValidCountry
    private String country;
    @NotEmpty
    @ValidCountryCode
    private String countryCode;
    @NotEmpty
    @Size(min = 5, max = 15)
    private String phoneNumber;
    @NotEmpty
    @Size(min = 4, max = 70)
    private String addressLine1;
    @NotEmpty
    @Size(min = 4, max = 70)
    private String addressLine2;
    @NotNull
    @AssertTrue
    private boolean tos;
    @NotNull
    private boolean newsletter;

    public @NotEmpty UUID getAuthId() {
        return authId;
    }

    public UserRegisterDTO setAuthId(@NotEmpty UUID authId) {
        this.authId = authId;
        return this;
    }

    public @NotEmpty @Size(min = 2, max = 60) String getFirstName() {
        return firstName;
    }

    public UserRegisterDTO setFirstName(@NotEmpty @Size(min = 2, max = 60) String firstName) {
        this.firstName = firstName;
        return this;
    }

    public @NotEmpty @Size(min = 2, max = 60) String getLastName() {
        return lastName;
    }

    public UserRegisterDTO setLastName(@NotEmpty @Size(min = 2, max = 60) String lastName) {
        this.lastName = lastName;
        return this;
    }

    public @NotNull @Past Date getDob() {
        return dob;
    }

    public UserRegisterDTO setDob(@NotNull @Past Date dob) {
        this.dob = dob;
        return this;
    }

    public @NotEmpty @Email String getEmail() {
        return email;
    }

    public UserRegisterDTO setEmail(@NotEmpty @Email String email) {
        this.email = email;
        return this;
    }

    public @NotEmpty @Size(min = 5, max = 60) String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(@NotEmpty @Size(min = 5, max = 60) String password) {
        this.password = password;
        return this;
    }

    public @NotEmpty String getCountry() {
        return country;
    }

    public UserRegisterDTO setCountry(@NotEmpty String country) {
        this.country = country;
        return this;
    }

    public @NotEmpty String getCountryCode() {
        return countryCode;
    }

    public UserRegisterDTO setCountryCode(@NotEmpty String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public @NotEmpty @Size(min = 5, max = 15) String getPhoneNumber() {
        return phoneNumber;
    }

    public UserRegisterDTO setPhoneNumber(@NotEmpty @Size(min = 5, max = 15) String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public @NotEmpty @Size(min = 4, max = 70) String getAddressLine1() {
        return addressLine1;
    }

    public UserRegisterDTO setAddressLine1(@NotEmpty @Size(min = 4, max = 70) String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public @NotEmpty @Size(min = 4, max = 70) String getAddressLine2() {
        return addressLine2;
    }

    public UserRegisterDTO setAddressLine2(@NotEmpty @Size(min = 4, max = 70) String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    @NotNull
    @AssertTrue
    public boolean isTos() {
        return tos;
    }

    public UserRegisterDTO setTos(@NotNull @AssertTrue boolean tos) {
        this.tos = tos;
        return this;
    }

    @NotNull
    public boolean isNewsletter() {
        return newsletter;
    }

    public UserRegisterDTO setNewsletter(@NotNull boolean newsletter) {
        this.newsletter = newsletter;
        return this;
    }
}
