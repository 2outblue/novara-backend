package com.novaraspace.model.entity;

import com.novaraspace.model.dto.user.AddCardDTO;
import com.novaraspace.model.dto.user.UserDocumentUpdateRequest;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

    //TODO: This should be a manyToMany
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<UserRole> roles;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private VerificationToken verification;

    private String title;
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

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "user_id")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_documents", joinColumns = @JoinColumn(name = "user_id"))
    @Size(max = 10)
    private List<UserDocument> documents = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_cards", joinColumns = @JoinColumn(name = "user_id"))
    @Size(max = 10)
    private List<UserPaymentCard> cards = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "users_bookings",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "booking_id"))
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany
    @JoinTable(name = "user_payments",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "payment_id"))
    private List<Payment> payments;

    public User() {
    }

    public void updateDocument(UserDocumentUpdateRequest req) {
        List<UserDocument> updatedDocs = documents.stream()
                .filter(doc -> !doc.getDocId().equals(req.getDocId())).collect(Collectors.toList());

        if (req.getAction().equals("upload")) {
            if (documents.size() >= 10) { return; };
            UserDocument document = new UserDocument()
                    .setDocId(req.getDocId())
                    .setFilename(req.getFilename())
                    .setUploadedOn(LocalDate.now());
            updatedDocs.add(document);
        }
        this.setDocuments(updatedDocs);
    }


    public void addCard(UserPaymentCard card) {
        if (cards.size() < 11) {
            cards.add(card);
        }
    }

    public void removeCard(String cardRef) {
        List<UserPaymentCard> updatedCards = cards.stream()
                .filter(c -> !c.getReference().equals(cardRef)).collect(Collectors.toList());
        this.setCards(updatedCards);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void addPayment(Payment payment) {
        List<Payment> sorted = payments.stream().sorted(
                Comparator.comparing(Payment::getCreatedAt)
        ).collect(Collectors.toList());
        if (sorted.size() >= 10) {
            sorted.removeFirst();
        }
        sorted.add(payment);
        this.setPayments(sorted);
//        payments.add(payment);
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

    public String getTitle() {
        return title;
    }

    public User setTitle(String title) {
        this.title = title;
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

    public List<UserDocument> getDocuments() {
        return documents;
    }

    public User setDocuments(List<UserDocument> documents) {
        this.documents = documents;
        return this;
    }

    public @Size(max = 10) List<UserPaymentCard> getCards() {
        return cards;
    }

    public User setCards(@Size(max = 10) List<UserPaymentCard> cards) {
        this.cards = cards;
        return this;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public User setBookings(List<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public User setPayments(List<Payment> payments) {
        this.payments = payments;
        return this;
    }
}
