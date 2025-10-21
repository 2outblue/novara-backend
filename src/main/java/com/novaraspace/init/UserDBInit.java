package com.novaraspace.init;

import com.nimbusds.jose.util.Base64;
import com.novaraspace.model.dto.user.UserRegisterDTO;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.UserRole;
import com.novaraspace.model.mapper.UserMapper;
import com.novaraspace.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
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


    private List<User> getInitUsers() {
        return Arrays.asList(
                new User()
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

                        .setCreatedAt(Instant.now())
                        .setAuthId(Base64.encode(UUID.randomUUID().toString()).toString())
                        .setStatus(AccountStatus.ACTIVE)
                        .setRoles(Set.of(UserRole.ADMIN))
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
