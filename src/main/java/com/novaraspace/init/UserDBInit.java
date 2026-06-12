package com.novaraspace.init;

import com.nimbusds.jose.util.Base64;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.entity.UserDocument;
import com.novaraspace.model.entity.UserPaymentCard;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.UserRole;
import com.novaraspace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@Order(1)
public class UserDBInit implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${app.user.adminInitialPass}")
    private String adminPass;

    @Value("${app.user.userInitialPass}")
    private String userPass;

    public UserDBInit(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.saveAll(getInitUsers());
        }
    }


    private List<User> getInitUsers() {
        return Arrays.asList(
                new User()
                        .setAccountNumber("89IADQZ2PDIU")
                        .setTitle("Mr")
                        .setFirstName("John")
                        .setLastName("Kelly")
                        .setDob(LocalDate.of(1985, 10, 20))
                        .setEmail("kelly@mail.com")
                        .setPassword(passwordEncoder.encode(adminPass))
                        .setCountry("Bulgaria")
                        .setCountryCode("+359")
                        .setPhoneNumber("891234567")
                        .setAddressLine1("Sofia, Bulgaria")
                        .setAddressLine2("Main str., 234, 1000")
                        .setNewsletter(false)
                        .setMarketing(false)
                        .setDocuments(Arrays.asList(
                                new UserDocument()
                                        .setDocId("SMC-002")
                                        .setFilename("my-file.pdf")
                                        .setUploadedOn(LocalDate.of(2026, 3, 7))
                        ))
                        .setCards(Arrays.asList(
                                new UserPaymentCard()
                                        .setReference("xpz6pdJiPYgmCONVnGE")
                                        .setFirstFour("2340")
                                        .setLastFour("3840")
                                        .setCardHolder("Kelly Goblin")
                                        .setCardType("Mastercard")
                                        .setExpiryDate("12/55")
                                        .setAddedOn(LocalDate.of(2026, 3, 10))
                        ))
                        .setCreatedAt(Instant.now())
                        .setAuthId(Base64.encode(UUID.randomUUID().toString()).toString())
                        .setStatus(AccountStatus.ACTIVE)
                        .setRoles(Set.of(UserRole.ADMIN, UserRole.USER))
                ,
                new User()
                        .setAccountNumber("CYAL8H1IRZT4")
                        .setFirstName("Douglas")
                        .setLastName("Ivanov")
                        .setDob(LocalDate.of(1981, 2, 7))
                        .setEmail("doug@e.com")
                        .setPassword(passwordEncoder.encode(adminPass))
                        .setCountry("Bulgaria")
                        .setCountryCode("+359")
                        .setPhoneNumber("891234567")
                        .setAddressLine1("Plovdiv, Bulgaria")
                        .setAddressLine2("Second str., 123, 1000")
                        .setNewsletter(false)
                        .setMarketing(false)

                        .setCreatedAt(Instant.now())
                        .setAuthId(Base64.encode(UUID.randomUUID().toString()).toString())
                        .setStatus(AccountStatus.ACTIVE)
                        .setRoles(Set.of(UserRole.ADMIN))
                ,
                new User()
                        .setAccountNumber("123K7M6KRTQX")
                        .setTitle("Mr")
                        .setFirstName("Public")
                        .setLastName("Admin1")
                        .setDob(LocalDate.of(1985, 10, 20))
                        .setEmail("paAdmin1@novara")
                        .setPassword(passwordEncoder.encode("parola"))
                        .setCountry("Bulgaria")
                        .setCountryCode("+359")
                        .setPhoneNumber("123456789")
                        .setAddressLine1("Sofia, Bulgaria")
                        .setAddressLine2("Main str., 234, 1000")
                        .setNewsletter(true)
                        .setMarketing(false)
                        .setDocuments(Arrays.asList(
                                new UserDocument()
                                        .setDocId("SMC-002")
                                        .setFilename("my-file.pdf")
                                        .setUploadedOn(LocalDate.of(2026, 4, 1))
                        ))
                        .setCards(Arrays.asList(
                                new UserPaymentCard()
                                        .setReference("xpz6pdJiPYgmCONVnGE")
                                        .setFirstFour("2340")
                                        .setLastFour("3840")
                                        .setCardHolder("Public Admin1")
                                        .setCardType("Mastercard")
                                        .setExpiryDate("12/55")
                                        .setAddedOn(LocalDate.of(2026, 4, 1))
                        ))
                        .setCreatedAt(Instant.now())
                        .setAuthId(Base64.encode(UUID.randomUUID().toString()).toString())
                        .setStatus(AccountStatus.ACTIVE)
                        .setRoles(Set.of(UserRole.USER))
                        .setDemo()
                ,
                new User()
                        .setAccountNumber("123A7F6KRTQX")
                        .setTitle("Ms")
                        .setFirstName("Public2")
                        .setLastName("Admin2")
                        .setDob(LocalDate.of(1985, 6, 20))
                        .setEmail("paAdmin2@novara")
                        .setPassword(passwordEncoder.encode("parola"))
                        .setCountry("Bulgaria")
                        .setCountryCode("+359")
                        .setPhoneNumber("123456789")
                        .setAddressLine1("Sofia, Bulgaria")
                        .setAddressLine2("Second str., 234, 1000")
                        .setNewsletter(true)
                        .setMarketing(false)
                        .setDocuments(Arrays.asList(
                                new UserDocument()
                                        .setDocId("SMC-002")
                                        .setFilename("my-file.pdf")
                                        .setUploadedOn(LocalDate.of(2026, 4, 1))
                        ))
                        .setCards(Arrays.asList(
                                new UserPaymentCard()
                                        .setReference("xpz6pdJiPYgmCONVnGE")
                                        .setFirstFour("2340")
                                        .setLastFour("3840")
                                        .setCardHolder("Public Admin2")
                                        .setCardType("Mastercard")
                                        .setExpiryDate("12/55")
                                        .setAddedOn(LocalDate.of(2026, 4, 1))
                        ))
                        .setCreatedAt(Instant.now())
                        .setAuthId(Base64.encode(UUID.randomUUID().toString()).toString())
                        .setStatus(AccountStatus.ACTIVE)
                        .setRoles(Set.of(UserRole.PUBLIC_ADMIN))
                        .setDemo()
                ,
                new User()
                        .setAccountNumber("124A7F6KOTBX")
                        .setTitle("Mr")
                        .setFirstName("John")
                        .setLastName("Smith")
                        .setDob(LocalDate.of(1995, 9, 20))
                        .setEmail("demoUser@novara")
                        .setPassword(passwordEncoder.encode("parola"))
                        .setCountry("Bulgaria")
                        .setCountryCode("+359")
                        .setPhoneNumber("123456789")
                        .setAddressLine1("Sofia, Bulgaria")
                        .setAddressLine2("Second str., 234, 1000")
                        .setNewsletter(true)
                        .setMarketing(false)
                        .setDocuments(Arrays.asList(
                                new UserDocument()
                                        .setDocId("SMC-002")
                                        .setFilename("some-file.pdf")
                                        .setUploadedOn(LocalDate.of(2026, 4, 1))
                        ))
                        .setCards(Arrays.asList(
                                new UserPaymentCard()
                                        .setReference("xpz6pdJiPYgmCONVnGE")
                                        .setFirstFour("2340")
                                        .setLastFour("3840")
                                        .setCardHolder("John Smith")
                                        .setCardType("Mastercard")
                                        .setExpiryDate("12/55")
                                        .setAddedOn(LocalDate.of(2026, 4, 1))
                        ))
                        .setCreatedAt(Instant.now())
                        .setAuthId(Base64.encode(UUID.randomUUID().toString()).toString())
                        .setStatus(AccountStatus.ACTIVE)
                        .setRoles(Set.of(UserRole.PUBLIC_ADMIN))
                        .setDemo()
        );
    }

}
