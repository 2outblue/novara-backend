package com.novaraspace.service;

import com.novaraspace.model.dto.auth.EmailDTO;
import com.novaraspace.model.dto.user.InitialAccountDataDTO;
import com.novaraspace.model.dto.user.UpdateFieldDTO;
import com.novaraspace.model.dto.user.UserRegisterDTO;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.entity.VerificationToken;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.exception.UserException;
import com.novaraspace.model.exception.VerificationException;
import com.novaraspace.model.mapper.UserMapper;
import com.novaraspace.repository.UserRepository;
import com.novaraspace.util.CountryCodeLoader;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final CountryCodeLoader countryDataLoader;
    private final Map<String, BiConsumer<User, Object>> fieldUpdaters;
    private final Validator validator;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, CountryCodeLoader countryDataLoader, Validator validator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.countryDataLoader = countryDataLoader;
        this.validator = validator;
        this.fieldUpdaters = setFieldUpdaters();
    }

    @Transactional
    public void activateUserAccount(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(VerificationException::failed);
        user.setStatus(AccountStatus.ACTIVE);
        user.setVerification(null);
    }

    @Transactional
    public void updateUserVerification(String email, VerificationToken verification) {
        User user = userRepository.findByEmail(email).orElseThrow(VerificationException::failed);
        user.setVerification(verification);
    }

    @Transactional
    public void setLastLoginNow(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Bad credentials."));
        user.setLastLoginAt(Instant.now());
    }

    public InitialAccountDataDTO getInitialAccountData(String authId) {
        User user = userRepository.findByAuthId(authId).orElseThrow(UserException::notFound);
        return userMapper.entityToInitialData(user);
    }

    public EmailDTO getEmailByAuthId(String authId) {
        String email = userRepository.getEmailByAuthId(authId).orElseThrow(UserException::notFound);
        return new EmailDTO().setEmail(email);
    }

    @Transactional
    public List<UpdateFieldDTO> updateFields(List<UpdateFieldDTO> updates, String authId) {
        if (updates == null || updates.isEmpty()) {throw UserException.fieldUpdateFailed();}

        User user = userRepository.findByAuthId(authId).orElseThrow(UserException::fieldUpdateFailed);
        for (UpdateFieldDTO dto : updates) {
            if (!dto.getOldValue().getClass().equals(dto.getNewValue().getClass())) {
                throw UserException.fieldUpdateFailed();
            }
            BiConsumer<User, Object> updater = fieldUpdaters.get(dto.getFieldName());
            updater.accept(user, dto.getNewValue());
        }

        UserRegisterDTO registerDTO = userMapper.userToRegisterDTO(user);
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(registerDTO); // Validate updates based on registerDTO constraints
        updates.forEach(el -> {
            violations.removeIf(v -> !el.getFieldName().equals(v.getPropertyPath().toString()));
        });
        if (!violations.isEmpty()) {
            throw UserException.fieldUpdateFailed();
        }
        return updates;
    }


//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email)
//                .map(this::entityToUserDetails)
//                .orElseThrow(() -> new BadCredentialsException("Bad credentials."));
//    }
//
//    private UserDetails entityToUserDetails(User entity) {
//        boolean isActive = entity.getStatus() == AccountStatus.ACTIVE;
//        return new org.springframework.security.core.userdetails.User(
//                entity.getEmail(),
//                entity.getPassword(),
//                isActive,
//                true,
//                true,
//                true,
//                entity.getRoles().stream()
//                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
//                        .collect(Collectors.toList())
//        );
//    }

    public User persistUser(User user) {
        return userRepository.save(user);
    }


    public Optional<User> findEntityByAuthId(String authId) {return userRepository.findByAuthId(authId);}
    public Optional<String> getAuthIdByEmail(String email) { return userRepository.getAuthIdByEmail(email);}
    public Optional<User> getEntityByEmail(String email) { return userRepository.findByEmail(email); }

    private Map<String, BiConsumer<User, Object>> setFieldUpdaters() {
        return Map.of(
                "countryCode", (u, v) -> {
                    if (v instanceof String) {u.setCountryCode((String) v);}
                    else {throw UserException.fieldUpdateFailed();}
                },
                "phoneNumber", (u, v) -> {
                    if (v instanceof String) {u.setPhoneNumber((String) v);}
                    else if (v instanceof Integer) {u.setPhoneNumber(Integer.toString((Integer)v));}
                    else {throw UserException.fieldUpdateFailed();}
                },
                "addressLine1", (u, v) -> {
                    if (v instanceof String) {u.setAddressLine1((String) v);}
                    else {throw UserException.fieldUpdateFailed();}
                },
                "addressLine2", (u, v) -> {
                    if (v instanceof String) {u.setAddressLine2((String) v);}
                    else {throw UserException.fieldUpdateFailed();}
                },
                "country", (u, v) -> {
                    if (v instanceof String) {u.setCountry((String) v);}
                    else {throw UserException.fieldUpdateFailed();}
                },
                "newsletter", (u, v) -> {
                    if (v instanceof Boolean) {u.setNewsletter((Boolean) v);}
                    else {throw UserException.fieldUpdateFailed();}
                },
                "marketing", (u, v) -> {
                    if (v instanceof Boolean) {u.setMarketing((Boolean) v);}
                    else {throw UserException.fieldUpdateFailed();}
                }
        );
    }
}
