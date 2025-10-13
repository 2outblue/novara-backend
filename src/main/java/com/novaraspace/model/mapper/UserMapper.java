package com.novaraspace.model.mapper;

import com.nimbusds.jose.util.Base64;
import com.novaraspace.model.dto.user.UserRegisterDTO;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.UserRole;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {
    @Value("${app.enable-email-verification}")
    private boolean emailVerificationEnabled;

    protected PasswordEncoder passwordEncoder;

    public UserMapper() {
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Mapping(target = "password", ignore = true)
    protected abstract User userRegisterDtoToEntity(UserRegisterDTO dto);

    public User registerToUser(UserRegisterDTO dto) {
        User user = userRegisterDtoToEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setCreatedAt(Instant.now());
        user.setAuthId(Base64.encode(UUID.randomUUID().toString()).toString());
        if (emailVerificationEnabled) {
            user.setStatus(AccountStatus.PENDING_ACTIVATION);
        } else {
            user.setStatus(AccountStatus.ACTIVE);
        }
        user.setRoles(Set.of(UserRole.USER));

        return user;
    }

}
