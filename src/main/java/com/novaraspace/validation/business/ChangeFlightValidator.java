package com.novaraspace.validation.business;


import com.novaraspace.model.dto.booking.ChangeFlightsRequest;
import com.novaraspace.model.entity.Booking;
import com.novaraspace.model.entity.BookingQuote;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.enums.CabinClassEnum;
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
        boolean flightChangePossible = booking.getDepartureFlight().departsAtLeast24HoursFromNow();
        boolean validPaymentAmount = validateChangeAgainstPayment(booking, request);
        boolean validNewFlights = validateNewFlights(booking, request);

        return flightChangePossible
                && validPaymentAmount
                && validNewFlights
                && !booking.isCancelled();
    }

    private boolean validateChangeAgainstPayment(Booking booking, ChangeFlightsRequest request) {
        int paxCount = booking.getPassengers().size();
        double paymentAmount = request.getPayment().getAmount();
        double depChangeFee = booking.getDepartureClass().getChangeFee() * paxCount;
        double retChangeFee = booking.getReturnFlight() == null
                ? 0
                : booking.getReturnClass().getChangeFee() * paxCount;
        double totalFeesAmount = depChangeFee + retChangeFee;

        double totalFlightDifferenceCost = getTotalFlightDifferenceCost(booking, request);

        double totalChangePrice = totalFeesAmount + totalFlightDifferenceCost;
        return DoublePricesUtil.areEqual(totalChangePrice, paymentAmount);
    }

    private double getTotalFlightDifferenceCost(Booking booking, ChangeFlightsRequest request) {
        double totalFlightDifferenceCost = 0;
        double oldDeparturePrice = booking.getDepartureFlightPrice();
        double newDeparturePrice = request.getDepartureFlight().getPrice();
        double priceDiff = newDeparturePrice - oldDeparturePrice;
        if (priceDiff > 0) { totalFlightDifferenceCost += priceDiff; }

        if (booking.getReturnFlight() != null && request.getReturnFlight() != null) {
            double oldReturnPrice = booking.getReturnFlightPrice();
            double newReturnPrice = request.getReturnFlight().getPrice();
            double returnPriceDiff = newReturnPrice - oldReturnPrice;
            if (returnPriceDiff > 0) { totalFlightDifferenceCost += returnPriceDiff; }
        }
        int paxCount = booking.getPassengers().size();
        return totalFlightDifferenceCost * paxCount;
    }

    private boolean validateNewFlights(Booking booking, ChangeFlightsRequest request) {
        BookingQuote quote = bookingQuoteRepository.findByReference(request.getQuoteReference())
                .orElse(null);
        if (quote == null) {return false;};

        String newDeparturePublicId = request.getDepartureFlight().getId();
        String newReturnPublicId = request.getReturnFlight() == null
                ? null
                : request.getReturnFlight().getId();
        FlightInstance newDepartureFlight = flightInstanceRepository.findByPublicId(newDeparturePublicId)
                .orElse(null);
        FlightInstance newReturnFlight = flightInstanceRepository.findByPublicId(newReturnPublicId)
                .orElse(null);
        if (newDepartureFlight == null) {return false;}

        boolean validDepartureDate = newDepartureFlight.departureDateIsBetween(
                quote.getDepartureLowerDate(), quote.getDepartureUpperDate());
        boolean validReturnDate = newReturnFlight == null
                || newReturnFlight.departureDateIsBetween(quote.getReturnLowerDate(), quote.getReturnUpperDate());

        boolean newDepFlightNotIdenticalToOld = !newDepartureFlight.getPublicId()
                .equals(booking.getDepartureFlight().getPublicId());
        boolean newRetFlightNotIdenticalToOld = true;
        if (booking.getReturnFlight() != null) {
            newRetFlightNotIdenticalToOld = newReturnFlight == null
                    || !newReturnFlight.getPublicId().equals(booking.getReturnFlight().getPublicId());
        }

        boolean returnFlightChangeExistOnTwoWayQuote = (newReturnFlight != null && !quote.isOneWay())
                || (newReturnFlight == null && quote.isOneWay());
        return validDepartureDate
                && validReturnDate
                && returnFlightChangeExistOnTwoWayQuote
                && newDepFlightNotIdenticalToOld
                && newRetFlightNotIdenticalToOld;
    }


}
