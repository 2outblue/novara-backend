package com.novaraspace.model.entity;

import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.UserRole;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

import static java.sql.Types.VARCHAR;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String publicId;
    private Instant createdAt;
    @JdbcTypeCode(VARCHAR)
    @Column(unique = true)
    private String authId;
    private AccountStatus status;
    private Instant lastLoginAt;
    private Instant deletedAt;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<UserRole> roles;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private VerificationToken verification;

    private String firstName;
    private String lastName;
    private Date dob;
    @Column(unique = true)
    private String email;
    private String password;
    private String country;
    private String countryCode;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private boolean newsletter;
    private boolean marketing = false;

    public User() {
    }

    public String getPublicId() {
        return publicId;
    }

    public User setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public User setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getAuthId() {
        return authId;
    }

    public User setAuthId(String authId) {
        this.authId = authId;
        return this;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public User setStatus(AccountStatus status) {
        this.status = status;
        return this;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    public User setLastLoginAt(Instant lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
        return this;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public User setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public User setRoles(Set<UserRole> roles) {
        this.roles = roles;
        return this;
    }

    public VerificationToken getVerification() {
        return verification;
    }

    public User setVerification(VerificationToken verification) {
        this.verification = verification;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Date getDob() {
        return dob;
    }

    public User setDob(Date dob) {
        this.dob = dob;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public User setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public User setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public User setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public User setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public User setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
        return this;
    }

    public boolean isMarketing() {
        return marketing;
    }

    public User setMarketing(boolean marketing) {
        this.marketing = marketing;
        return this;
    }
}
