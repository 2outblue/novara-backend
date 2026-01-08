package com.novaraspace.service;

import com.nimbusds.jose.util.Base64;
import com.novaraspace.model.dto.booking.BookingQuoteDTO;
import com.novaraspace.model.dto.flight.FlightSearchQueryDTO;
import com.novaraspace.model.dto.flight.FlightSearchResultDTO;
import com.novaraspace.model.entity.BookingQuote;
import com.novaraspace.model.exception.BookingException;
import com.novaraspace.model.mapper.BookingMapper;
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
    private final BookingMapper bookingMapper;
    private final BookingQuoteRepository bookingQuoteRepository;

    public BookingQuoteService(Validator validator, BookingMapper bookingMapper, BookingQuoteRepository bookingQuoteRepository) {
        this.validator = validator;
        this.bookingMapper = bookingMapper;
        this.bookingQuoteRepository = bookingQuoteRepository;
    }

    public BookingQuoteDTO createNewQuote(FlightSearchQueryDTO searchQuery, FlightSearchResultDTO searchResult) {
        BookingQuoteDTO dto = new BookingQuoteDTO()
                .setQuoteId(Base64.encode(UUID.randomUUID().toString()).toString())
                .setExpiresAt(LocalDateTime.now().plusHours(1))
                .setOneWay(!searchQuery.hasReturnFlight())
                .setPaxCount(searchQuery.getPaxCount())
                .setDepartureCode(searchQuery.getDepartureCode())
                .setArrivalCode(searchQuery.getArrivalCode())
                .setDepartureLowerDate(searchResult.getLimits().getDepartureLowerDate())
                .setDepartureUpperDate(searchResult.getLimits().getDepartureUpperDate())
                .setArrivalLowerDate(searchResult.getLimits().getArrivalLowerDate())
                .setArrivalUpperDate(searchResult.getLimits().getArrivalUpperDate());
        boolean bookingQuoteValid = checkBookingQuoteDTOValid(dto);
        if (!bookingQuoteValid) {
            throw BookingException.invalidQuote();
        }

        BookingQuote newQuote = bookingMapper.bookingQuoteDtoToEntity(dto);
        BookingQuote persistedQuote = bookingQuoteRepository.save(newQuote);
        return bookingMapper.entityToBookingQuoteDto(persistedQuote);
    }



    private boolean checkBookingQuoteDTOValid(BookingQuoteDTO dto) {
        Set<ConstraintViolation<BookingQuoteDTO>> violations = validator.validate(dto);
        return violations.isEmpty();
    }
}
