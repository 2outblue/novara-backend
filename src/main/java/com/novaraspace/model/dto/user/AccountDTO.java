package com.novaraspace.model.dto.user;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class AccountDTO {

    private String publicId;
    private Instant createdAt;
    private String title;
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
    private List<UserDocumentDTO> documents;
    private List<UserCardDTO> cards;

    public AccountDTO() {
    }

    public String getPublicId() {
        return publicId;
    }

    public AccountDTO setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public AccountDTO setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AccountDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AccountDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AccountDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Date getDob() {
        return dob;
    }

    public AccountDTO setDob(Date dob) {
        this.dob = dob;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AccountDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public AccountDTO setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AccountDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public AccountDTO setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public AccountDTO setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public AccountDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public AccountDTO setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
        return this;
    }

    public boolean isMarketing() {
        return marketing;
    }

    public AccountDTO setMarketing(boolean marketing) {
        this.marketing = marketing;
        return this;
    }

    public List<UserDocumentDTO> getDocuments() {
        return documents;
    }

    public AccountDTO setDocuments(List<UserDocumentDTO> documents) {
        this.documents = documents;
        return this;
    }

    public List<UserCardDTO> getCards() {
        return cards;
    }

    public AccountDTO setCards(List<UserCardDTO> cards) {
        this.cards = cards;
        return this;
    }
}
