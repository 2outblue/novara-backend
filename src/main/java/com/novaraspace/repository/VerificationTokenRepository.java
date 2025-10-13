package com.novaraspace.repository;

import com.novaraspace.model.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    boolean existsByCode(String code);

    Optional<VerificationToken> findByCode(String code);
    Optional<VerificationToken> findByLinkToken(String linkToken);

}
