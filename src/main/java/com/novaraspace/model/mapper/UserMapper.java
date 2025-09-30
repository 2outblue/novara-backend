package com.novaraspace.model.mapper;

import com.nimbusds.jose.util.Base64;
import com.novaraspace.model.dto.user.UserRegisterDTO;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    protected PasswordEncoder passwordEncoder;

    public UserMapper() {
    }

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Mapping(target = "password", ignore = true)
    protected abstract User userRegisterDtoToEntity(UserRegisterDTO dto);

    public User registerToUser(UserRegisterDTO dto) {
        User user = userRegisterDtoToEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setCreatedAt(Instant.now());
        user.setAuthId(Base64.encode(UUID.randomUUID().toString()).toString());
        user.setStatus(AccountStatus.PENDING_ACTIVATION);
        user.setRoles(Set.of(UserRole.USER));

        return user;
    }

}
