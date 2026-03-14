package com.novaraspace.repository;

import com.novaraspace.model.domain.UserBookingsQuery;
import com.novaraspace.model.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByReference(String reference);

    Optional<Booking> findByReference(String reference);

//    @Query("""
//    select b from User u join u.bookings b where u.id = :#{#params.userId}
//    and (b.returnFlight is null OR (cast(b.returnFlight.departureDate as localdatetime) >= :#{#params.minDate}
//        and cast(b.returnFlight.departureDate as localdatetime) < :#{#params.maxDate}))
//    or (cast(b.departureFlight.departureDate as localdatetime) >= :#{#params.minDate}
//        and cast(b.departureFlight.departureDate as localdatetime) < :#{#params.maxDate})
//""")
//    Page<Booking> getUserUpcomingBookings(@Param("params") UserBookingsQuery params, Pageable pageable);
//
//    @Query("""
//    select b from User u join u.bookings b where u.id = :#{#params.userId}
//    and (b.returnFlight is null OR (cast(b.returnFlight.arrivalDate as localdatetime) <= :#{#params.maxDate}
//        and cast(b.returnFlight.departureDate as localdatetime) > :#{#params.minDate}))
//    and (cast(b.departureFlight.arrivalDate as localdatetime) <= :#{#params.maxDate}
//        and cast(b.departureFlight.departureDate as localdatetime) > :#{#params.minDate})
//""")
//    Page<Booking> getUserBookingsHistory(@Param("params") UserBookingsQuery params, Pageable pageable);
}
