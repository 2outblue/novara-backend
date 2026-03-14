package com.novaraspace.web;

import com.novaraspace.model.dto.booking.*;
import com.novaraspace.model.dto.flight.FlightSearchParamsDTO;
import com.novaraspace.model.other.PageResponse;
import com.novaraspace.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<BookingStartResultDTO> startBookingProcess(@Valid @RequestBody FlightSearchParamsDTO paramsDTO) {
        BookingStartResultDTO result = bookingService.getResultForNewBookingStart(paramsDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/complete")
    public ResponseEntity<BookingConfirmedDTO> createBooking(@Valid @RequestBody BookingCreateRequest createRequest) {
//        String authId = "";
//        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
//            authId = auth.getName();
//        }

        BookingConfirmedDTO confirmedBooking = bookingService.createNewBooking(createRequest);
        return ResponseEntity.ok(confirmedBooking);
    }

    @PostMapping("/manage")
    public ResponseEntity<BookingDTO> manageBooking(@Valid @RequestBody BookingSearchParams searchParams) {
        BookingDTO dto = bookingService.findBooking(searchParams);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/cancel")
    public ResponseEntity<BookingDTO> cancelBooking(@Valid @RequestBody BookingSearchParams searchParams) {
        BookingDTO dto = bookingService.cancelBooking(searchParams);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/change-flights-start")
    public ResponseEntity<ChangeFlightsStartResponse> changeFlightsStart(@RequestBody ChangeFlightsStartRequest request) {
        ChangeFlightsStartResponse response = bookingService.getChangeFlightsStartResponse(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-flights-complete")
    public ResponseEntity<BookingDTO> performFlightChange(@Valid @RequestBody ChangeFlightsRequest request) {
        BookingDTO bookingDTO = bookingService.changeFlights(request);
        return ResponseEntity.ok(bookingDTO);
    }


}
