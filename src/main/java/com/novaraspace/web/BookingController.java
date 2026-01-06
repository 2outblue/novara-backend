package com.novaraspace.web;

import com.novaraspace.model.dto.booking.NewBookingDTO;
import com.novaraspace.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<Void> createBooking(@Valid @RequestBody NewBookingDTO bookingDTO) {
        bookingService.createNewBooking(bookingDTO);
        return ResponseEntity.ok().build();
    }
}
