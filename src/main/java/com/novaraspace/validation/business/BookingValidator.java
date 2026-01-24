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

        FlightInstance departureFlight = booking.getDepartureFlight();
        FlightInstance returnFlight = booking.getReturnFlight();

        boolean departureFlightExists = departureFlight != null;
        boolean returnFlightExistOnTwoWayBooking = returnFlight != null && !quote.isOneWay();

        boolean departureFlightValidDates = departureFlight == null
                || departureFlight.departureDateIsBetween(quote.getDepartureLowerDate(), quote.getDepartureUpperDate());
        boolean returnFlightValidDates = returnFlight == null
                || returnFlight.departureDateIsBetween(quote.getArrivalLowerDate(), quote.getArrivalUpperDate());

        boolean validFlightClasses = checkValidCabinClasses(booking);

        return departureFlightExists
                && returnFlightExistOnTwoWayBooking
                && departureFlightValidDates
                && returnFlightValidDates
                && validFlightClasses;
    }

//    private boolean checkFlightDatesWithinQuotedLimits(FlightInstance flight, BookingQuote quote) {
//        LocalDateTime date = flight.getDepartureDate();
//        LocalDate lowerLimitDate = quote.getDepartureLowerDate();
//        LocalDate upperLimitDate = quote.getDepartureUpperDate();
//
//        boolean notBeforeLowerLimit = date.isAfter(lowerLimitDate.minusDays(1).atTime(LocalTime.MAX));
//        boolean notAfterUpperLimit = date.isBefore(upperLimitDate.plusDays(1).atTime(LocalTime.MIN));
//        return notBeforeLowerLimit && notAfterUpperLimit;
//    }

    private boolean checkValidCabinClasses(Booking booking) {
        boolean validDepartureClass = booking.getDepartureClass() != null;
        boolean validReturnClass = booking.getReturnFlight() == null || booking.getReturnClass() != null;
        return validDepartureClass && validReturnClass;
    }
}
