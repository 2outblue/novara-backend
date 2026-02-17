package com.novaraspace.validation.business;

import com.novaraspace.model.entity.*;
import com.novaraspace.model.enums.CabinClassEnum;
import com.novaraspace.repository.BookingQuoteRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookingValidator {

    private final BookingQuoteRepository bookingQuoteRepository;

    public BookingValidator(BookingQuoteRepository bookingQuoteRepository) {
        this.bookingQuoteRepository = bookingQuoteRepository;
    }

    public boolean validateBookingAgainstPayment(Booking booking, Payment payment) {
        if (booking == null || payment == null || payment.getAmount() == null) {return false;}

        double paymentAmount = payment.getAmount();
        double bookingPrice = booking.getTotalPrice();
        return normalizeDoublePrice(paymentAmount) == normalizeDoublePrice(bookingPrice);
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
        boolean returnFlightExistOnTwoWayBooking = returnFlight == null || !quote.isOneWay();

        boolean departureFlightValidDates = departureFlight == null
                || departureFlight.departureDateIsBetween(quote.getDepartureLowerDate(), quote.getDepartureUpperDate());
        boolean returnFlightValidDates = returnFlight == null
                || returnFlight.departureDateIsBetween(quote.getReturnLowerDate(), quote.getReturnUpperDate());

        boolean validFlightClasses = checkValidCabinClasses(booking);
        boolean validPrices = validatePrices(booking);
        boolean uniqueExtraServices = uniqueExtraServices(booking);

        return departureFlightExists
                && returnFlightExistOnTwoWayBooking
                && departureFlightValidDates
                && returnFlightValidDates
                && validFlightClasses
                && validPrices
                && uniqueExtraServices;
    }

    private boolean checkValidCabinClasses(Booking booking) {
        boolean validDepartureClass = booking.getDepartureClass() != null;
        boolean validReturnClass = booking.getReturnFlight() == null || booking.getReturnClass() != null;
        return validDepartureClass && validReturnClass;
    }

    //TODO: Include a small error margin anyway.
    //TODO: Just use the DoublePriceUtil to check equality
    private boolean validatePrices(Booking booking) {
        boolean validFlightPrices = checkValidFlightPrices(booking);
        boolean matchingPaxBaggagePrices = checkPaxBaggageMatchesServicePrice(booking);

        return validFlightPrices && matchingPaxBaggagePrices;
    }

    private boolean checkValidFlightPrices(Booking booking) {
        int paxCount = booking.getPassengers().size();
        CabinClassEnum departureClass = booking.getDepartureClass();
        double departureTargetPrice = booking.getDepartureFlight().getPrice(departureClass) * paxCount;
        double departureActualPrice = booking.getDepartureFlightPrice();

        boolean validDeparturePrice = normalizeDoublePrice(departureTargetPrice)
                == normalizeDoublePrice(departureActualPrice);

        if (booking.getReturnFlight() == null) {
            boolean returnPriceNullOrZero = booking.getReturnFlightPrice() == null
                    || booking.getReturnFlightPrice() == 0;
            return returnPriceNullOrZero && validDeparturePrice;
        }
        CabinClassEnum returnClass = booking.getReturnClass();
        double returnTargetPrice = booking.getReturnFlight().getPrice(returnClass) * paxCount;
        double actualReturnPrice = booking.getReturnFlightPrice();
        boolean validReturnPrice = normalizeDoublePrice(returnTargetPrice)
                == normalizeDoublePrice(actualReturnPrice);

        return validDeparturePrice && validReturnPrice;
    }

    private boolean uniqueExtraServices(Booking booking) {
        if (booking == null || booking.getExtraServices() == null) {
            return true;
        }
        List<ExtraService> services = booking.getExtraServices();
        return services.stream()
                .map(ExtraService::getCode)
                .distinct()
                .count() == services.size();
    }

    // If you need that anywhere else, put it in the booking entity;
    private boolean checkPaxBaggageMatchesServicePrice(Booking booking) {
        if (booking.getPassengers() == null || booking.getExtraServices() == null) {
            return false;
        }
        double accumulatedBaggagePrice = booking.getPassengers().stream()
                .filter(pax -> pax.getBaggage() != null)
                .map(pax -> pax.getBaggage().getPrice())
                .mapToDouble(Double::doubleValue)
                .sum();
        double baggageServicePrice = booking.getExtraServices().stream()
                .filter(s -> s.getCode().equals("baggage"))
                .findFirst().map(ExtraService::getPrice)
                .orElse(0.0);

        return normalizeDoublePrice(accumulatedBaggagePrice) ==
                normalizeDoublePrice(baggageServicePrice);
    }

    private double normalizeDoublePrice(double price) {
        return Math.round(price * 100.0) / 100.0;
    }
}
