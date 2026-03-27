package com.novaraspace.model.dto.admin.publicAdmin;

import com.novaraspace.model.enums.AccountStatus;

import java.time.Instant;

public class PaUserDetailsDTO {
    private Long id;
    private Instant createdAt;
    private AccountStatus status;
    private Instant lastLoginAt;
    private Long totalLogins;
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

    public PaUserDetailsDTO setId(Long id) {
        this.id = 0L;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public PaUserDetailsDTO setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public PaUserDetailsDTO setStatus(AccountStatus status) {
        this.status = status;
        return this;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    public PaUserDetailsDTO setLastLoginAt(Instant lastLoginAt) {
        this.lastLoginAt = Instant.EPOCH;
        return this;
    }

    public Long getTotalLogins() {
        return totalLogins;
    }

    public PaUserDetailsDTO setTotalLogins(Long totalLogins) {
        this.totalLogins = totalLogins;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PaUserDetailsDTO setEmail(String email) {
        this.email = "****";
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PaUserDetailsDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public PaUserDetailsDTO setFirstName(String firstName) {
        this.firstName = "John";
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PaUserDetailsDTO setLastName(String lastName) {
        this.lastName = "Doe";
        return this;
    }

    public String getCountry() {
        return country;
    }

    public PaUserDetailsDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public PaUserDetailsDTO setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PaUserDetailsDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = "******";
        return this;
    }

    public double getTotalInvoiced() {
        return totalInvoiced;
    }

    public PaUserDetailsDTO setTotalInvoiced(double totalInvoiced) {
        this.totalInvoiced = totalInvoiced;
        return this;
    }

    public int getSavedCardsCount() {
        return savedCardsCount;
    }

    public PaUserDetailsDTO setSavedCardsCount(int savedCardsCount) {
        this.savedCardsCount = savedCardsCount;
        return this;
    }

    public int getBookingsCount() {
        return bookingsCount;
    }

    public PaUserDetailsDTO setBookingsCount(int bookingsCount) {
        this.bookingsCount = bookingsCount;
        return this;
    }

    public int getPaymentsCount() {
        return paymentsCount;
    }

    public PaUserDetailsDTO setPaymentsCount(int paymentsCount) {
        this.paymentsCount = paymentsCount;
        return this;
    }
}
