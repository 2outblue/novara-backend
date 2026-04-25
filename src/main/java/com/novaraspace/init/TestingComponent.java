package com.novaraspace.init;

import com.novaraspace.init.data.TestingDTO;
import com.novaraspace.repository.BookingRepository;
import com.novaraspace.repository.PaymentRepository;
import com.novaraspace.repository.RefreshTokenRepository;
import com.novaraspace.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

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

//    private final UserService userService;
//
//    public TestingComponent(UserService userService) {
//        this.userService = userService;
//    }
//
//    private final String[] arr = new String[]{"kelly@mail.com", "doug@e.com", "paAdmin1@novara"};
//
//
//    @PostConstruct
//    public void init() {
//        for (String s : arr) {
//            System.out.println(userService.createAccountNumber(s));
//        }
//    }

    public void deletePayment(TestingDTO tDto) {
        paymentRepository.deleteJoinTableRowsByReference(tDto.getId());
        paymentRepository.deleteById(tDto.getId());
    }

    public void deleteRefreshToken(TestingDTO tDto) {
        refreshTokenRepository.deleteById(tDto.getId());
    }

    public void deleteBooking(TestingDTO tDto) {
        bookingRepository.deleteById(tDto.getId());
    }
}
