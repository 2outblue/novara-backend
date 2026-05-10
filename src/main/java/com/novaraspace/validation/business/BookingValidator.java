package com.novaraspace.validation.business;

import com.novaraspace.model.domain.BookingValidationParams;
import com.novaraspace.model.dto.booking.ServicesPricingOffer;
import com.novaraspace.model.entity.*;
import com.novaraspace.model.enums.BaggageCapacity;
import com.novaraspace.model.enums.CabinClassEnum;
import com.novaraspace.model.enums.ExtraServiceCode;
import com.novaraspace.model.enums.PaxAgeGroup;
import com.novaraspace.util.DoublePricesUtil;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BookingValidator {


    public boolean validateNewBooking(BookingValidationParams params) {
        Booking booking = params.booking();
        BookingQuote quote = params.quote();
        if (!nonNullParams(params)) {return false;}

        FlightInstance departureFlight = booking.getDepartureFlight();
        FlightInstance returnFlight = booking.getReturnFlight();

        boolean departureFlightValidDates = departureFlight == null
                || departureFlight.departureDateIsBetween(quote.getDepartureLowerDate(), quote.getDepartureUpperDate());
        boolean returnFlightValidDates = returnFlight == null
                || returnFlight.departureDateIsBetween(quote.getReturnLowerDate(), quote.getReturnUpperDate());

        boolean validPrices = validatePrices(params);
        boolean uniqueExtraServices = uniqueExtraServices(booking);

        return departureFlightValidDates
                && returnFlightValidDates
                && validPrices
                && uniqueExtraServices
                && validPaxAgeGroups(booking.getPassengers());
    }

    private boolean nonNullParams(BookingValidationParams params) {
        Booking booking = params.booking();
        BookingQuote quote = params.quote();
        Payment payment = params.payment();
        return booking != null && quote != null && payment != null
                && booking.getDepartureFlight() != null
                && booking.getDepartureClass() != null
                && booking.getExtraServices() != null
                && booking.getPassengers() != null
                && !booking.getPassengers().isEmpty()
                && payment.getAmount() != null
                && quote.getServicesPricing() != null
                && (booking.getReturnFlight() == null || !quote.isOneWay())
                && (booking.getReturnFlight() == null || booking.getReturnClass() != null);

    }

    private boolean validPaxAgeGroups(List<Passenger> passengers) {
        return passengers.stream().allMatch(pax -> {
            PaxAgeGroup ageGroup = pax.getAgeGroup();
            return ageGroup.validAge(pax.getDob());
        });
    }

    private boolean validatePrices(BookingValidationParams params) {
        return validFlightPrices(params.booking())
                && paymentMatchesBookingCost(params)
                && validServicePrices(params);
    }

    private boolean validFlightPrices(Booking booking) {
        int paxCount = booking.getPassengers().size();
        CabinClassEnum departureClass = booking.getDepartureClass();
        double departureTargetPrice = booking.getDepartureFlight().getPrice(departureClass) * paxCount;
        double departureActualPrice = booking.getDepartureFlightPrice();

        boolean validDeparturePrice = DoublePricesUtil.areEqual(departureTargetPrice, departureActualPrice);

        if (booking.getReturnFlight() == null) {
            boolean returnPriceNullOrZero = booking.getReturnFlightPrice() == null
                    || booking.getReturnFlightPrice() == 0;
            return returnPriceNullOrZero && validDeparturePrice;
        }
        CabinClassEnum returnClass = booking.getReturnClass();
        double returnTargetPrice = booking.getReturnFlight().getPrice(returnClass) * paxCount;
        double actualReturnPrice = booking.getReturnFlightPrice();
        boolean validReturnPrice = DoublePricesUtil.areEqual(returnTargetPrice, actualReturnPrice);

        return validDeparturePrice && validReturnPrice;
    }

    private boolean uniqueExtraServices(Booking booking) {
        List<ExtraService> services = booking.getExtraServices();
        return services.stream()
                .map(ExtraService::getCode)
                .distinct()
                .count() == services.size();
    }

    private boolean paymentMatchesBookingCost(BookingValidationParams params) {
        double paymentAmount = params.payment().getAmount();
        double bookingPrice = params.booking().getTotalPrice();
        return DoublePricesUtil.areEqual(paymentAmount, bookingPrice);
    }

    private boolean validServicePrices(BookingValidationParams params) {
        Booking booking = params.booking();
        ServicesPricingOffer servicesOffer = params.quote().getServicesPricing();
        Map<ExtraServiceCode, Double> servicesPrices = servicesOffer.getServicesPrices();
        List<ExtraService> services = booking.getExtraServices();

        boolean validSimpleServicePrices = services.stream().allMatch(s -> {
            ExtraServiceCode code = s.getCode();
            if (code.equals(ExtraServiceCode.baggage) || code.equals(ExtraServiceCode.shared)) {
                return true;
            }
            return s.getPrice() == servicesPrices.get(code);
        });

        return validSimpleServicePrices
                && allBaggageMatchesTotalServicePrice(booking)
                && individualBaggageMatchesQuotedPrice(params)
                && validSharedCabinPrices(params);
    }

    private boolean validSharedCabinPrices(BookingValidationParams params) {
        List<Passenger> passengers = params.booking().getPassengers();
        if (passengers.size() <= 1) { return true; }

        List<ExtraService> extraServices = params.booking().getExtraServices();
        ExtraService sharedService = extraServices.stream()
                .filter(s -> s.getCode().equals(ExtraServiceCode.shared))
                .findFirst().orElse(null);
        if (sharedService == null) { return true; }

        long uniqueCabinOwnersCount = passengers.stream()
                .filter(Passenger::isOwned).map(Passenger::getCabinId)
                .distinct()
                .count();

        Map<ExtraServiceCode, Double> servicesPrices = params.quote().getServicesPricing().getServicesPrices();
        double perCabinQuotedSharedPrice = servicesPrices.get(ExtraServiceCode.shared);

        double sharedCabinsTargetPrice = uniqueCabinOwnersCount * perCabinQuotedSharedPrice;
        double sharedCabinsActualPrice = sharedService.getPrice();
        return DoublePricesUtil.areEqual(sharedCabinsTargetPrice, sharedCabinsActualPrice);
    }

    private boolean individualBaggageMatchesQuotedPrice(BookingValidationParams params) {
        List<Passenger> passengers = params.booking().getPassengers();
        Map<BaggageCapacity, Double> baggagePrices = params.quote().getServicesPricing().getBaggagePrices();
        return passengers.stream().allMatch(pax -> {
            BaggageCapacity capacity = pax.getBaggage().getCapacity();
            if (capacity == null) { return true; }

            double realPrice = pax.getBaggage().getPrice();
            double targetPrice = baggagePrices.get(capacity);
            return DoublePricesUtil.areEqual(realPrice, targetPrice);
        });
    }


    private boolean allBaggageMatchesTotalServicePrice(Booking booking) {
        double accumulatedBaggagePrice = booking.getPassengers().stream()
                .filter(pax -> pax.getBaggage() != null)
                .map(pax -> pax.getBaggage().getPrice())
                .mapToDouble(Double::doubleValue)
                .sum();
        double baggageServicePrice = booking.getExtraServices().stream()
                .filter(s -> s.getCode().equals(ExtraServiceCode.baggage))
                .findFirst().map(ExtraService::getPrice)
                .orElse(0.0);

        return DoublePricesUtil.areEqual(accumulatedBaggagePrice, baggageServicePrice);
    }

}
