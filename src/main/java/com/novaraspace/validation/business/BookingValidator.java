package com.novaraspace.validation.business;

import com.novaraspace.model.entity.Booking;
import com.novaraspace.model.entity.BookingQuote;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.repository.BookingQuoteRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Component
public class BookingValidator {

    private final BookingQuoteRepository bookingQuoteRepository;

    public BookingValidator(BookingQuoteRepository bookingQuoteRepository) {
        this.bookingQuoteRepository = bookingQuoteRepository;
    }

    public boolean validateNewBooking(Booking booking, String bookingQuoteRef) {
        if (booking == null || bookingQuoteRef == null) {return false;}

        //TODO: Also need validation for pax age group when that is correctly implemented in the frontend,
        // + pricing validation (the quote needs to be modified)
        Optional<BookingQuote> optionalQuote = bookingQuoteRepository.findByReference(bookingQuoteRef);
        if (optionalQuote.isEmpty()) {return false;}
        BookingQuote quote = optionalQuote.get();

        boolean departureFlightExists = booking.getDepartureFlight() != null;
        boolean returnFlightExistOnTwoWayBooking = booking.getReturnFlight() != null && !quote.isOneWay();
        boolean departureFlightValidDates = checkFlightDatesWithinQuotedLimits(booking.getDepartureFlight(), quote);
        boolean returnFlightValidDates = booking.getReturnFlight() == null
                || checkFlightDatesWithinQuotedLimits(booking.getReturnFlight(), quote);

        boolean validFlightClasses = checkValidCabinClasses(booking);

        return departureFlightExists
                && returnFlightExistOnTwoWayBooking
                && departureFlightValidDates
                && returnFlightValidDates
                && validFlightClasses;
    }

    private boolean checkFlightDatesWithinQuotedLimits(FlightInstance flight, BookingQuote quote) {
        LocalDateTime date = flight.getDepartureDate();
        LocalDate lowerLimitDate = quote.getDepartureLowerDate();
        LocalDate upperLimitDate = quote.getDepartureUpperDate();

        boolean notBeforeLowerLimit = date.isAfter(lowerLimitDate.minusDays(1).atTime(LocalTime.MAX));
        boolean notAfterUpperLimit = date.isBefore(upperLimitDate.plusDays(1).atTime(LocalTime.MIN));
        return notBeforeLowerLimit && notAfterUpperLimit;
    }

    private boolean checkValidCabinClasses(Booking booking) {
        boolean validDepartureClass = booking.getDepartureClass() != null;
        boolean validReturnClass = booking.getReturnFlight() == null || booking.getReturnClass() != null;
        return validDepartureClass && validReturnClass;
    }
}
