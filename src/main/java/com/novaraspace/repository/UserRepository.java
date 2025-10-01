package com.novaraspace.repository;

import com.novaraspace.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
