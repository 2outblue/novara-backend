package com.novaraspace.repository;

import com.novaraspace.model.entity.RefreshToken;
//import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
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

    @Modifying
    @Query("update RefreshToken t set t.revoked = true where t.userAuthId = :authId")
    @Transactional
    void revokeByUserAuthId(@Param("authId") String authId);


    @Query("select t.userAuthId from RefreshToken t where cast(t.createdOn as localdatetime) >= :date and t.revoked = false")
    List<String> getAllAuthIdsForActiveUsers(LocalDateTime date, Pageable pageable);

//    Integer countByCreatedOnAfter(LocalDateTime date);

    @Query("select count(distinct t.userAuthId) from RefreshToken t where cast(t.createdOn as localdatetime) >= :date and t.revoked = false")
    Integer getCountForActiveUsers(LocalDateTime date);

//    @Transactional
    Integer deleteAllByExpiryDateBeforeOrRevokedTrue(Instant beforeDate);
}
