package com.novaraspace.init;

import com.novaraspace.init.data.TestingDTO;
import com.novaraspace.model.dto.flight.AvailableFlightDTO;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.mapper.FlightMapper;
import com.novaraspace.repository.BookingRepository;
import com.novaraspace.repository.FlightInstanceRepository;
import com.novaraspace.repository.PaymentRepository;
import com.novaraspace.repository.RefreshTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class TestingComponent {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final FlightInstanceRepository flightInstanceRepository;

    private final FlightMapper flightMapper;

    public TestingComponent(RefreshTokenRepository refreshTokenRepository, PaymentRepository paymentRepository, BookingRepository bookingRepository, FlightInstanceRepository flightInstanceRepository, FlightMapper flightMapper) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.flightInstanceRepository = flightInstanceRepository;
        this.flightMapper = flightMapper;
    }


    @Transactional
    public void deletePayment(TestingDTO tDto) {
        paymentRepository.deleteUserPaymentsRowsOlderThan(tDto.getDate());
        paymentRepository.deleteByCreatedAtBeforeAndBookingConfirmIsFalse(tDto.getDate());
    }

    @Transactional
    public void deleteRefreshToken(TestingDTO tDto) {
        refreshTokenRepository.deleteById(tDto.getId());
    }

    @Transactional
    public void deleteBooking(TestingDTO tDto) {
//        bookingRepository.deleteById(tDto.getId());
//        LocalDateTime now = LocalDateTime.now();
        bookingRepository.deleteUserBookingsRowsBefore(tDto.getDate());
        bookingRepository.deleteAllByCreatedAtBefore(tDto.getDate());
    }

    @Transactional
    public void deleteFlights(TestingDTO tDto) {
        int deletedFLights = flightInstanceRepository.deleteUnbookedFlightsDepartingBefore(tDto.getDate());
        System.out.println("DELETED FLIGHT RECORDS:");
        System.out.println(deletedFLights);
    }

    @Transactional
    public void deleteBookedFlights(TestingDTO tDto) {
        int deletedFlights = flightInstanceRepository.deleteAllByArrivalDateBefore(tDto.getDate());
        System.out.println("DELETED TOTAL FLIGHT RECORDS:");
        System.out.println(deletedFlights);
    }

    @Transactional
    public AvailableFlightDTO getLatestFlight() {
        Optional<FlightInstance> fi = flightInstanceRepository.findTopByOrderByDepartureDateDesc();
        return fi.map(flightMapper::instanceToAvailableFlightDTO).orElse(null);
    }

//    @Transactional
//    public void cleanUpNonBookedFlights() {
//        LocalDateTime cleanupDate = LocalDateTime.now().minusMonths(unbookedCleanupMonthsBack);
//        flightInstanceRepository.deleteUnbookedFlightsDepartingBefore(cleanupDate);
//    }
//
//    @Transactional
//    public void cleanUpBookedFlights() {
//        LocalDateTime oldestExistingBookingDate = LocalDateTime.now().minusMonths(bookingCleanupMonthsBack);
//        LocalDateTime safeDateForFlightCleanup = oldestExistingBookingDate.minusMonths(1);
//
//        flightInstanceRepository.deleteAllByArrivalDateBefore(safeDateForFlightCleanup);
//    }
}
