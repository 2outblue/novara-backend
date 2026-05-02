package com.novaraspace.repository;


import com.novaraspace.model.domain.UserBookingsQuery;
import com.novaraspace.model.domain.UserStatusParams;
import com.novaraspace.model.dto.admin.ChangeUserStatusRequestDTO;
import com.novaraspace.model.dto.admin.UserControlSearchDTO;
import com.novaraspace.model.dto.admin.UsersStatusRequestDTO;
import com.novaraspace.model.entity.Booking;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.enums.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByAuthId(String authId);
    Optional<User> findByEmail(String email);
    Optional<User> findByAuthId(String authId);

    @Query("""
    select b from User u join u.bookings b where u.id = :#{#params.userId}
    and ((b.returnFlight is null OR (cast(b.returnFlight.departureDate as localdatetime) >= :#{#params.minDate}
        and cast(b.returnFlight.departureDate as localdatetime) < :#{#params.maxDate}))
      or (cast(b.departureFlight.departureDate as localdatetime) >= :#{#params.minDate}
        and cast(b.departureFlight.departureDate as localdatetime) < :#{#params.maxDate}))
    and b.cancelled = false
""")
    Page<Booking> getUserUpcomingBookings(@Param("params") UserBookingsQuery params, Pageable pageable);

    @Query("""
    select b from User u join u.bookings b where u.id = :#{#params.userId}
    and (
        b.cancelled = true OR
        ((b.returnFlight is null OR (cast(b.returnFlight.arrivalDate as localdatetime) <= :#{#params.maxDate}
            and cast(b.returnFlight.departureDate as localdatetime) > :#{#params.minDate}))
          and (cast(b.departureFlight.arrivalDate as localdatetime) <= :#{#params.maxDate}
            and cast(b.departureFlight.departureDate as localdatetime) > :#{#params.minDate}))
        )
""")
    Page<Booking> getUserBookingsHistory(@Param("params") UserBookingsQuery params, Pageable pageable);


    List<User> findAllByAuthIdIn(List<String> authIds);

    Integer countByLastLoginAtAfter(Instant lastLoginAt);
    Integer countByCreatedAtAfter(Instant createdAt);
    @Query("select u.email from User u where u.lastLoginAt >= :date")
    List<String> findAllEmailsByLastLoginAtAfter(@Param("date") Instant date, Pageable pageable);

    @Query("select u.email from User u where u.createdAt >= :date")
    List<String> findAllEmailsByCreatedAtAfter(@Param("date") Instant date, Pageable pageable);

    @Query("select count(u) from User u where u.createdAt >= :#{#params.date} and u.status = :#{#params.status}")
    Integer countForStatus(@Param("params") UserStatusParams params);
    @Query("select u.email from User u where u.createdAt >= :#{#params.date} and u.status = :#{#params.status}")
    List<String> findUserEmailsForStatus(@Param("params") UserStatusParams params, Pageable pageable);


    @Query("""
    select u from User u where (:#{#params.dateFrom} is null or u.createdAt >= :#{#params.dateFrom})
    and (:#{#params.dateTo} is null or u.createdAt <= :#{#params.dateTo})
    and (:#{#params.idFrom} is null or u.id >= :#{#params.idFrom})
    and (:#{#params.idTo} is null or u.id <= :#{#params.idTo})
    and (:#{#params.email} is null or u.email = :#{#params.email})
    """)
    Page<User> getPageForUcSearch(@Param("params") UserControlSearchDTO params, Pageable pageable);

    @Modifying
    @Query("update User u set u.status = :#{#params.status} where u.id = :#{#params.id}")
    @Transactional
    int updateUserStatus(@Param("params") ChangeUserStatusRequestDTO params);

    int deleteAllByCreatedAtBeforeAndStatusIs(Instant createdAtBefore, AccountStatus status);
}
