package com.novaraspace.web;

import com.novaraspace.model.dto.booking.BookingConfirmedDTO;
import com.novaraspace.model.dto.booking.BookingStartResultDTO;
import com.novaraspace.model.dto.booking.NewBookingDTO;
import com.novaraspace.model.dto.flight.FlightSearchQueryDTO;
import com.novaraspace.service.BookingService;
import com.novaraspace.service.FlightService;
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
    private final FlightService flightService;


    public BookingController(BookingService bookingService, FlightService flightService) {
        this.bookingService = bookingService;
        this.flightService = flightService;
    }

    @PostMapping("/start")
    public ResponseEntity<BookingStartResultDTO> startBookingProcess(@RequestBody FlightSearchQueryDTO queryDTO) {
        BookingStartResultDTO result = bookingService.getResultForNewBookingStart(queryDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/complete")
    public ResponseEntity<BookingConfirmedDTO> createBooking(@Valid @RequestBody NewBookingDTO bookingDTO) {
        BookingConfirmedDTO confirmedBooking = bookingService.createNewBooking(bookingDTO);
        return ResponseEntity.ok(confirmedBooking);
    }


}
