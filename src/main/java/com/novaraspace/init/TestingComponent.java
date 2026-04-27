package com.novaraspace.init;

import com.novaraspace.init.data.TestingDTO;
import com.novaraspace.repository.BookingRepository;
import com.novaraspace.repository.PaymentRepository;
import com.novaraspace.repository.RefreshTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestingComponent {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public TestingComponent(RefreshTokenRepository refreshTokenRepository, PaymentRepository paymentRepository, BookingRepository bookingRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
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
}
