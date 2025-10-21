package com.novaraspace.repository;

import com.novaraspace.model.entity.User;
import com.novaraspace.model.entity.VerificationToken;
import com.novaraspace.model.enums.AccountStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByAuthId(String authId);

    @Query("select u.authId as authId from User u where u.email = :email")
    Optional<String> getAuthIdByEmail(@Param("email") String email);

    @Query("select u.email as email from User u where u.authId = :authId")
    Optional<String> getEmailByAuthId(@Param("authId") String authId);

    @Modifying
    @Query("update User u set u.status = :newStatus where u.id = :userId")
    @Transactional
    void updateUserStatusById(@Param("userId") Long userId, @Param("newStatus") AccountStatus newStatus);

    @Modifying
    @Query("update User u set u.verification = :verification where u.id = :userId")
    @Transactional
    void updateVerification(@Param("userId") Long userId, @Param("verification")VerificationToken verification);
}
