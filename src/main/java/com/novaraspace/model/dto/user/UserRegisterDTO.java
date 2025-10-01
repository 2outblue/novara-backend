package com.novaraspace.model.dto.user;

import com.novaraspace.validation.annotations.UniqueUserEmail;
import com.novaraspace.validation.annotations.ValidCountry;
import com.novaraspace.validation.annotations.ValidCountryCode;
import jakarta.validation.constraints.*;

import java.util.Date;

public class UserRegisterDTO {
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
    @Email(message = "Invalid email format.")
    @UniqueUserEmail
    private String email;
    @NotEmpty
    @Size(min = 5, max = 60)
    private String password; //TODO: Probably make a custom password annotation and validator.
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

    public UserRegisterDTO() {}


    public String getFirstName() {
        return firstName;
    }

    public UserRegisterDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Date getDob() {
        return dob;
    }

    public UserRegisterDTO setDob(Date dob) {
        this.dob = dob;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserRegisterDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public UserRegisterDTO setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserRegisterDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public UserRegisterDTO setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public UserRegisterDTO setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public boolean isTos() {
        return tos;
    }

    public UserRegisterDTO setTos(boolean tos) {
        this.tos = tos;
        return this;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public UserRegisterDTO setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
        return this;
    }

}
