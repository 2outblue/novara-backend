package com.novaraspace.web;

import com.novaraspace.model.dto.booking.NewBookingDTO;
import com.novaraspace.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/new")
    public ResponseEntity<Void> createBooking(NewBookingDTO bookingDTO) {
        bookingService.createNewBooking(bookingDTO);
        return ResponseEntity.ok().build();
    }
}
