package com.novaraspace.repository;

import com.novaraspace.model.entity.RefreshToken;
//import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserAuthId(String userAuthId);
    Optional<RefreshToken> findByPublicKey(String publicKey);

    @Modifying
    @Query("update RefreshToken t set t.revoked = true where t.familyId = :familyId and t.revoked = false")
    @Transactional
    void revokeByFamilyId(@Param("familyId") UUID familyId);

    @Modifying
    @Query("update RefreshToken t set t.revoked = true where t.publicKey = :publicKey")
    @Transactional
    void revokeByPublicKey(@Param("publicKey") String publicKey);
}
