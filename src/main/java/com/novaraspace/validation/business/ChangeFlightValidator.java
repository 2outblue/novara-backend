package com.novaraspace.validation.business;


import com.novaraspace.model.dto.booking.ChangeFlightsRequest;
import com.novaraspace.model.entity.Booking;
import com.novaraspace.model.entity.BookingQuote;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.exception.BookingException;
import com.novaraspace.repository.BookingQuoteRepository;
import com.novaraspace.repository.FlightInstanceRepository;
import com.novaraspace.util.DoublePricesUtil;
import org.springframework.stereotype.Component;

@Component
public class ChangeFlightValidator {

    private final FlightInstanceRepository flightInstanceRepository;
    private final BookingQuoteRepository bookingQuoteRepository;

    public ChangeFlightValidator(FlightInstanceRepository flightInstanceRepository, BookingQuoteRepository bookingQuoteRepository) {
        this.flightInstanceRepository = flightInstanceRepository;
        this.bookingQuoteRepository = bookingQuoteRepository;
    }


    public boolean validateFlightChange(Booking booking, ChangeFlightsRequest request) {
        boolean validDepartureDate = booking.getDepartureFlight().departsAtLeast24HoursFromNow();
        boolean validReturnDate = booking.getReturnFlight() == null
                || booking.getReturnFlight().departsAtLeast24HoursFromNow();
        boolean validPaymentAmount = validateChangeAgainstPayment(booking, request.getPayment().getAmount());
        boolean validNewFlights = validateNewFlights(request);

        return validDepartureDate
                && validReturnDate
                && validPaymentAmount
                && validNewFlights
                && !booking.isCancelled();
    }

    private boolean validateChangeAgainstPayment(Booking booking, double paymentAmount) {
        int paxCount = booking.getPassengers().size();
        double depChangeFee = booking.getDepartureClass().getChangeFee() * paxCount;
        double retChangeFee = booking.getReturnFlight() == null
                ? 0
                : booking.getReturnClass().getChangeFee() * paxCount;

        double totalChangePrice = depChangeFee + retChangeFee;
        return DoublePricesUtil.areEqual(totalChangePrice, paymentAmount);
    }

    //TODO: Maybe don't throw in here - just return false?
    private boolean validateNewFlights(ChangeFlightsRequest request) {
        BookingQuote quote = bookingQuoteRepository.findByReference(request.getQuoteReference())
                .orElseThrow(BookingException::changeFailed);

        String departurePublicId = request.getDepartureFlight().getId();
        String returnPublicId = request.getReturnFlight() == null
                ? null
                : request.getReturnFlight().getId();
        FlightInstance departureFlight = flightInstanceRepository.findByPublicId(departurePublicId)
                .orElseThrow(BookingException::changeFailed);
        FlightInstance returnFlight = flightInstanceRepository.findByPublicId(returnPublicId)
                .orElse(null);

        boolean validDepartureDate = departureFlight.departureDateIsBetween(
                quote.getDepartureLowerDate(), quote.getDepartureUpperDate());
        boolean validReturnDate = returnFlight == null
                || returnFlight.departureDateIsBetween(quote.getReturnLowerDate(), quote.getReturnUpperDate());

        boolean returnFlightChangeExistOnTwoWayBooking = returnFlight == null || !quote.isOneWay(); //TODO: Remove/change this if im going to implement departure change only

        return validDepartureDate && validReturnDate && returnFlightChangeExistOnTwoWayBooking;
    }


}
