package com.novaraspace.web;

import com.novaraspace.model.dto.booking.*;
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

    public BookingController(BookingService bookingService ) {
        this.bookingService = bookingService;
    }

    @PostMapping("/start")
    public ResponseEntity<BookingStartResultDTO> startBookingProcess(@RequestBody FlightSearchQueryDTO queryDTO) {
        BookingStartResultDTO result = bookingService.getResultForNewBookingStart(queryDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/complete")
    public ResponseEntity<BookingConfirmedDTO> createBooking(@Valid @RequestBody BookingCreateRequest createRequest) {
        BookingConfirmedDTO confirmedBooking = bookingService.createNewBooking(createRequest);
        return ResponseEntity.ok(confirmedBooking);
    }

    @PostMapping("/manage")
    public ResponseEntity<Void> manageBooking(@Valid @RequestBody BookingSearchParams searchParams) {

        return ResponseEntity.ok().build();
    }


}
