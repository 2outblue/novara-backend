package com.novaraspace.service;

import com.novaraspace.model.dto.booking.BookingQuoteDTO;
import com.novaraspace.model.dto.booking.BookingRequestResultDTO;
import com.novaraspace.model.dto.booking.NewBookingDTO;
import com.novaraspace.model.dto.flight.FlightSearchQueryDTO;
import com.novaraspace.model.dto.flight.FlightSearchResultDTO;
import com.novaraspace.model.entity.Booking;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.exception.BookingException;
import com.novaraspace.model.mapper.BookingMapper;
import com.novaraspace.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class BookingService {


    private final BookingMapper bookingMapper;
    private final FlightService flightService;
    private final BookingQuoteService bookingQuoteService;
    private final BookingRepository bookingRepository;

    public BookingService(BookingMapper bookingMapper, FlightService flightService, BookingQuoteService bookingQuoteService, BookingRepository bookingRepository) {
        this.bookingMapper = bookingMapper;
        this.flightService = flightService;
        this.bookingQuoteService = bookingQuoteService;
        this.bookingRepository = bookingRepository;
    }

    public BookingRequestResultDTO getResultForNewBookingStart(FlightSearchQueryDTO flightSearchQueryDTO) {
        FlightSearchResultDTO flightSearchResultDTO = flightService.getFlightSearchResult(flightSearchQueryDTO);
        BookingQuoteDTO bookingQuote = bookingQuoteService.createNewQuote(flightSearchQueryDTO, flightSearchResultDTO);

        return new BookingRequestResultDTO()
                .setFlightSearchResult(flightSearchResultDTO)
                .setQuoteRef(bookingQuote.getReference());
    }

    @Transactional
    public void createNewBooking(NewBookingDTO dto) {
        Booking booking = bookingMapper.newBookingDtoToEntity(dto);
        boolean validBooking = validateNewBooking(booking, dto.getQuoteReference());
        if (!validBooking) {throw BookingException.invalidCreationData();}
        //TODO: Create a payment entity and persist the booking in the DB

    }

    private boolean validateNewBooking(Booking booking, String bookingQuoteRef) {
        //TODO: Also need validation for pax age group when that is correctly implemented in the frontend,
        // + pricing validation (the quote needs to be modified)
        Optional<BookingQuoteDTO> optionalQuote = bookingQuoteService.getQuoteByReference(bookingQuoteRef);
        if (optionalQuote.isEmpty()) {return false;}
        BookingQuoteDTO quoteDTO = optionalQuote.get();

        boolean returnFlightExistOnTwoWayBooking =
                booking.getReturnFlight() != null && !quoteDTO.isOneWay();
        boolean departureFlightValidDates = checkFlightDatesWithinQuotedLimits(booking.getDepartureFlight(), quoteDTO);
        boolean returnFlightValidDates = booking.getReturnFlight() == null
                        || checkFlightDatesWithinQuotedLimits(booking.getReturnFlight(), quoteDTO);

        return returnFlightExistOnTwoWayBooking
                && departureFlightValidDates
                && returnFlightValidDates;
    }

    private boolean checkFlightDatesWithinQuotedLimits(FlightInstance flight, BookingQuoteDTO quote) {
        LocalDateTime date = flight.getDepartureDate();
        LocalDate lowerLimitDate = quote.getDepartureLowerDate();
        LocalDate upperLimitDate = quote.getDepartureUpperDate();

        boolean notBeforeLowerLimit = date.isAfter(lowerLimitDate.minusDays(1).atTime(LocalTime.MAX));
        boolean notAfterUpperLimit = date.isBefore(upperLimitDate.plusDays(1).atTime(LocalTime.MIN));
        return notBeforeLowerLimit && notAfterUpperLimit;
    }





}
