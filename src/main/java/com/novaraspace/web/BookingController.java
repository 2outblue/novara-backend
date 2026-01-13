package com.novaraspace.web;

import com.novaraspace.model.dto.booking.BookingRequestResultDTO;
import com.novaraspace.model.dto.booking.NewBookingDTO;
import com.novaraspace.model.dto.flight.FlightSearchQueryDTO;
import com.novaraspace.model.dto.flight.FlightSearchResultDTO;
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
    public ResponseEntity<BookingRequestResultDTO> startBookingProcess(@RequestBody FlightSearchQueryDTO queryDTO) {
        BookingRequestResultDTO result = bookingService.getResultForNewBookingStart(queryDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> createBooking(@Valid @RequestBody NewBookingDTO bookingDTO) {
        bookingService.createNewBooking(bookingDTO);
        return ResponseEntity.ok().build();
    }


}
