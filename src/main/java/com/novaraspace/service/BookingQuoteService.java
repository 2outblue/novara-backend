package com.novaraspace.service;

import com.nimbusds.jose.util.Base64;
import com.novaraspace.model.domain.BookingQuoteParams;
import com.novaraspace.model.dto.booking.BookingQuoteDTO;
import com.novaraspace.model.dto.booking.ServicesPricingOffer;
import com.novaraspace.model.dto.flight.FlightSearchParamsDTO;
import com.novaraspace.model.dto.flight.FlightSearchResultDTO;
import com.novaraspace.model.entity.BookingQuote;
import com.novaraspace.model.exception.BookingException;
import com.novaraspace.model.mapper.BookingQuoteMapper;
import com.novaraspace.repository.BookingQuoteRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class BookingQuoteService {

    private final Validator validator;
    private final BookingQuoteMapper mapper;
    private final BookingQuoteRepository quoteRepository;

    public BookingQuoteService(Validator validator, BookingQuoteMapper mapper, BookingQuoteRepository quoteRepository) {
        this.validator = validator;
        this.mapper = mapper;
        this.quoteRepository = quoteRepository;
    }


    public BookingQuote getValidQuoteByReference(String reference) {
        return quoteRepository.findByReference(reference).orElseThrow(BookingException::invalidQuote);
    }

    public String createQuoteWithoutServicesOffer(BookingQuoteParams params) {
        BookingQuote quote = getGeneralValidQuote(params);
        return quoteRepository.save(quote).getReference();
    }

    public String createQuoteForNewBooking(BookingQuoteParams params) {
        BookingQuote quote = getGeneralValidQuote(params);

        ServicesPricingOffer servicesOffer = params.servicesPricing();
        if (servicesOffer == null || !servicesOffer.looksValid()) {
            throw BookingException.invalidQuote();
        }
        quote.setServicesPricing(servicesOffer);
        BookingQuote persistedQuote = quoteRepository.save(quote);
        return persistedQuote.getReference();
    }

    private BookingQuote getGeneralValidQuote(BookingQuoteParams params) {
        FlightSearchParamsDTO searchParams = params.flightSearchParams();
        FlightSearchResultDTO searchResult = params.flightSearchResult();
        BookingQuoteDTO dto = new BookingQuoteDTO()
                .setReference(Base64.encode(UUID.randomUUID().toString()).toString())
                .setExpiresAt(LocalDateTime.now().plusHours(1))
                .setOneWay(!searchParams.hasReturnFlight())
                .setPaxCount(searchParams.getTotalPaxCount())
                .setDepartureCode(searchParams.getDepartureCode())
                .setArrivalCode(searchParams.getArrivalCode())
                .setDepartureLowerDate(searchResult.getLimits().getDepartureLowerDate())
                .setDepartureUpperDate(searchResult.getLimits().getDepartureUpperDate())
                .setReturnLowerDate(searchResult.getLimits().getReturnLowerDate())
                .setReturnUpperDate(searchResult.getLimits().getReturnUpperDate());
        boolean bookingQuoteValid = checkBookingQuoteDTOValid(dto);
        if (!bookingQuoteValid) {
            throw BookingException.invalidQuote();
        }

        return mapper.dtoToEntity(dto);
    }

    private boolean checkBookingQuoteDTOValid(BookingQuoteDTO dto) {
        Set<ConstraintViolation<BookingQuoteDTO>> violations = validator.validate(dto);
        return violations.isEmpty();
    }
}
