package com.novaraspace.init;

import com.nimbusds.jose.util.Base64;
import com.novaraspace.model.dto.user.UserRegisterDTO;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.entity.UserDocument;
import com.novaraspace.model.entity.UserPaymentCard;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.UserRole;
import com.novaraspace.model.mapper.UserMapper;
import com.novaraspace.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@Order(1)
public class UserDBInit implements CommandLineRunner {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserDBInit(UserMapper userMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.saveAll(getInitUsers());
        }
    }

//    private void initLocations() {
//        Location location = new Location()
//                .setRegion(FlightRegion.NEAR_EARTH)
//                .setLocation(FlightRegion.FlightLocation.Mars.ACIDALIA_PLANITIA);
//    }


    private List<User> getInitUsers() {
        return Arrays.asList(
                new User()
                        .setTitle("Mr")
                        .setFirstName("John")
                        .setLastName("Kelly")
                        .setDob(Date.valueOf("1985-10-20"))
                        .setEmail("kelly@mail.com")
                        .setPassword(passwordEncoder.encode("parola"))
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
                        .setFirstName("Douglas")
                        .setLastName("Ivanov")
                        .setDob(Date.valueOf("1981-02-07"))
                        .setEmail("doug@e.com")
                        .setPassword(passwordEncoder.encode("parola"))
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
                        .setTitle("Mr")
                        .setFirstName("Public")
                        .setLastName("Admin1")
                        .setDob(Date.valueOf("1985-10-20"))
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
                        .setRoles(Set.of(UserRole.PUBLIC_ADMIN))
        );
    }

    private List<UserRegisterDTO> getInitDTOs() {
        return Arrays.asList(
                new UserRegisterDTO()
                        .setFirstName("John")
                        .setLastName("Kelly")
                        .setDob(Date.valueOf("1985-10-20"))
                        .setEmail("kelly@mail.com")
                        .setPassword(passwordEncoder.encode("parola"))
                        .setCountry("Bulgaria")
                        .setCountryCode("+359")
                        .setPhoneNumber("891234567")
                        .setAddressLine1("Sofia, Bulgaria")
                        .setAddressLine2("Main str., 234, 1000")
                        .setTos(true)
                        .setNewsletter(false)
                ,
                new UserRegisterDTO()
                        .setFirstName("Francis")
                        .setLastName("Doe")
                        .setDob(Date.valueOf("1977-09-16"))
                        .setEmail("mail@c")
                        .setPassword(passwordEncoder.encode("12345"))
                        .setCountry("Bulgaria")
                        .setCountryCode("+359")
                        .setPhoneNumber("891234567")
                        .setAddressLine1("Sofia, Bulgaria")
                        .setAddressLine2("Main str., 234, 1000")
                        .setTos(true)
                        .setNewsletter(false)
        );
    }
}
