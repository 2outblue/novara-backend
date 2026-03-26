package com.novaraspace.model.mapper;

import com.nimbusds.jose.util.Base64;
import com.novaraspace.model.dto.admin.UserControlResult;
import com.novaraspace.model.dto.admin.UserDetailsDTO;
import com.novaraspace.model.dto.user.AccountDTO;
import com.novaraspace.model.dto.user.UserCardDTO;
import com.novaraspace.model.dto.user.UserDocumentDTO;
import com.novaraspace.model.dto.user.UserRegisterDTO;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.entity.UserDocument;
import com.novaraspace.model.entity.UserPaymentCard;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

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

    public abstract AccountDTO entityToAccountDTO(User user);

    @Mapping(target = "password", ignore = true)
    protected abstract User userRegisterDtoToEntity(UserRegisterDTO dto);

    public User registerToUser(UserRegisterDTO dto) {
        User user = userRegisterDtoToEntity(dto);

        user.setPublicId(UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase());
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

    public abstract UserRegisterDTO userToRegisterDTO(User user);

    public abstract UserDocumentDTO documentToDto(UserDocument document);
    public abstract UserCardDTO userCardToDTO(UserPaymentCard card);
    public abstract UserControlResult entityToUcResult(User entity);

    public UserDetailsDTO entityToUcDetails(User entity) {
        UserDetailsDTO partialDTO = entityToPartialUcDetails(entity);
        return partialDTO
                .setSavedCardsCount(entity.getCards().size())
                .setBookingsCount(entity.getBookings().size())
                .setPaymentsCount(entity.getPayments().size());
    }
    protected abstract UserDetailsDTO entityToPartialUcDetails(User entity);

}
