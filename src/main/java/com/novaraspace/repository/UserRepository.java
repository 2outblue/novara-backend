package com.novaraspace.repository;


import com.novaraspace.model.domain.UserBookingsQuery;
import com.novaraspace.model.entity.Booking;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.entity.VerificationToken;
import com.novaraspace.model.enums.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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


    @Query("""
    select b from User u join u.bookings b where u.id = :#{#params.userId}
    and ((b.returnFlight is null OR (cast(b.returnFlight.departureDate as localdatetime) >= :#{#params.minDate}
        and cast(b.returnFlight.departureDate as localdatetime) < :#{#params.maxDate}))
    or (cast(b.departureFlight.departureDate as localdatetime) >= :#{#params.minDate}
        and cast(b.departureFlight.departureDate as localdatetime) < :#{#params.maxDate}))
""")
    Page<Booking> getUserUpcomingBookings(@Param("params") UserBookingsQuery params, Pageable pageable);

    @Query("""
    select b from User u join u.bookings b where u.id = :#{#params.userId}
    and ((b.returnFlight is null OR (cast(b.returnFlight.arrivalDate as localdatetime) <= :#{#params.maxDate}
        and cast(b.returnFlight.departureDate as localdatetime) > :#{#params.minDate}))
    and (cast(b.departureFlight.arrivalDate as localdatetime) <= :#{#params.maxDate}
        and cast(b.departureFlight.departureDate as localdatetime) > :#{#params.minDate}))
""")
    Page<Booking> getUserBookingsHistory(@Param("params") UserBookingsQuery params, Pageable pageable);


    List<User> findAllByAuthIdIn(List<String> authIds);
}
