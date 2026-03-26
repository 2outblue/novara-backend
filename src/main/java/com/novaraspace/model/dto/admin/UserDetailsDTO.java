package com.novaraspace.model.dto.admin;

import com.novaraspace.model.enums.AccountStatus;

import java.time.Instant;

public class UserDetailsDTO {

    private Long id;
    private Instant createdAt;
    private AccountStatus status;
    private Instant lastLoginAt;
    private String email;
    private String title;
    private String firstName;
    private String lastName;
    private String country;
    private String countryCode;
    private String phoneNumber;
    private double totalInvoiced;

    private int savedCardsCount;
    private int bookingsCount;
    private int paymentsCount;

    public Long getId() {
        return id;
    }

    public UserDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UserDetailsDTO setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public UserDetailsDTO setStatus(AccountStatus status) {
        this.status = status;
        return this;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    public UserDetailsDTO setLastLoginAt(Instant lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDetailsDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public UserDetailsDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDetailsDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDetailsDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserDetailsDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public UserDetailsDTO setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserDetailsDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public double getTotalInvoiced() {
        return totalInvoiced;
    }

    public UserDetailsDTO setTotalInvoiced(double totalInvoiced) {
        this.totalInvoiced = totalInvoiced;
        return this;
    }

    public int getSavedCardsCount() {
        return savedCardsCount;
    }

    public UserDetailsDTO setSavedCardsCount(int savedCardsCount) {
        this.savedCardsCount = savedCardsCount;
        return this;
    }

    public int getBookingsCount() {
        return bookingsCount;
    }

    public UserDetailsDTO setBookingsCount(int bookingsCount) {
        this.bookingsCount = bookingsCount;
        return this;
    }

    public int getPaymentsCount() {
        return paymentsCount;
    }

    public UserDetailsDTO setPaymentsCount(int paymentsCount) {
        this.paymentsCount = paymentsCount;
        return this;
    }
}
