package com.novaraspace.service;

import com.nimbusds.jose.util.Base64;
import com.novaraspace.model.dto.booking.BookingQuoteDTO;
import com.novaraspace.model.dto.flight.FlightSearchQueryDTO;
import com.novaraspace.model.dto.flight.FlightSearchResultDTO;
import com.novaraspace.model.entity.BookingQuote;
import com.novaraspace.model.exception.BookingException;
import com.novaraspace.model.mapper.BookingQuoteMapper;
import com.novaraspace.repository.BookingQuoteRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
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

    Optional<BookingQuote> findQuoteByReference(String reference) {
        return quoteRepository.findByReference(reference);
    }

    public BookingQuoteDTO createNewQuote(FlightSearchQueryDTO searchQuery, FlightSearchResultDTO searchResult) {
        BookingQuoteDTO dto = new BookingQuoteDTO()
                .setReference(Base64.encode(UUID.randomUUID().toString()).toString())
                .setExpiresAt(LocalDateTime.now().plusHours(1))
                .setOneWay(!searchQuery.hasReturnFlight())
                .setPaxCount(searchQuery.getTotalPaxCount())
                .setDepartureCode(searchQuery.getDepartureCode())
                .setArrivalCode(searchQuery.getArrivalCode())
                .setDepartureLowerDate(searchResult.getLimits().getDepartureLowerDate())
                .setDepartureUpperDate(searchResult.getLimits().getDepartureUpperDate())
                .setArrivalLowerDate(searchResult.getLimits().getReturnLowerDate())
                .setArrivalUpperDate(searchResult.getLimits().getReturnUpperDate());
        boolean bookingQuoteValid = checkBookingQuoteDTOValid(dto);
        if (!bookingQuoteValid) {
            throw BookingException.invalidQuote();
        }

        BookingQuote newQuote = mapper.dtoToEntity(dto);
        BookingQuote persistedQuote = quoteRepository.save(newQuote);
        return mapper.entityToDto(persistedQuote);
    }

    private boolean checkBookingQuoteDTOValid(BookingQuoteDTO dto) {
        Set<ConstraintViolation<BookingQuoteDTO>> violations = validator.validate(dto);
        return violations.isEmpty();
    }
}
