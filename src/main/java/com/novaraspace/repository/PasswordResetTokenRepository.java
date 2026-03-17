package com.novaraspace.repository;

import com.novaraspace.model.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByTokenValue(String tokenValue);
    Optional<PasswordResetToken> findByUserAuthId(String authId);

    void deleteAllByUserAuthId(String userAuthId);

    Integer deleteByTokenValue(String tokenValue);
}
