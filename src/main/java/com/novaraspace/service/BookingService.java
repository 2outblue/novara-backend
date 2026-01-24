package com.novaraspace.service;

import com.novaraspace.component.BookingReferenceGenerator;
import com.novaraspace.model.domain.CreatePaymentCommand;
import com.novaraspace.model.dto.booking.BookingConfirmedDTO;
import com.novaraspace.model.dto.booking.BookingQuoteDTO;
import com.novaraspace.model.dto.booking.BookingStartResultDTO;
import com.novaraspace.model.dto.booking.NewBookingDTO;
import com.novaraspace.model.dto.flight.FlightSearchQueryDTO;
import com.novaraspace.model.dto.flight.FlightSearchResultDTO;
import com.novaraspace.model.entity.Booking;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.entity.Payment;
import com.novaraspace.model.enums.CabinClassEnum;
import com.novaraspace.model.exception.BookingException;
import com.novaraspace.model.mapper.BookingMapper;
import com.novaraspace.repository.BookingRepository;
import com.novaraspace.validation.business.BookingValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {


    private final BookingMapper bookingMapper;
    private final FlightService flightService;
    private final BookingQuoteService bookingQuoteService;
    private final BookingRepository bookingRepository;
    private final BookingValidator bookingValidator;
    private final BookingReferenceGenerator bookingReferenceGenerator;
    private final PaymentService paymentService;

    public BookingService(
            BookingMapper bookingMapper,
            FlightService flightService,
            BookingQuoteService bookingQuoteService,
            BookingRepository bookingRepository,
            BookingValidator bookingValidator,
            BookingReferenceGenerator bookingReferenceGenerator,
            PaymentService paymentService) {
        this.bookingMapper = bookingMapper;
        this.flightService = flightService;
        this.bookingQuoteService = bookingQuoteService;
        this.bookingRepository = bookingRepository;
        this.bookingValidator = bookingValidator;
        this.bookingReferenceGenerator = bookingReferenceGenerator;
        this.paymentService = paymentService;
    }

    public BookingStartResultDTO getResultForNewBookingStart(FlightSearchQueryDTO flightSearchQueryDTO) {
        FlightSearchResultDTO flightSearchResultDTO = flightService.getFlightSearchResult(flightSearchQueryDTO);
        BookingQuoteDTO bookingQuote = bookingQuoteService.createNewQuote(flightSearchQueryDTO, flightSearchResultDTO);

        return new BookingStartResultDTO()
                .setFlightSearchResult(flightSearchResultDTO)
                .setQuoteRef(bookingQuote.getReference());
    }

    @Transactional
    public BookingConfirmedDTO createNewBooking(NewBookingDTO dto) {
        Booking booking = bookingMapper.newBookingDtoToEntity(dto);
        
        FlightInstance departureFlight = flightService.findFlightByPublicId(dto.getDepartureFlightId())
                .orElseThrow(BookingException::creationFailed);
        FlightInstance returnFlight = flightService.findFlightByPublicId(dto.getReturnFlightId())
                .orElse(null);
        booking.setDepartureFlight(departureFlight);
        booking.setReturnFlight(returnFlight);

        boolean validBooking = bookingValidator.validateNewBooking(booking, dto.getQuoteReference());
        if (!validBooking) {throw BookingException.creationFailed();}

        int paxCount = booking.getPassengers().size();
        departureFlight.reserveSeats(booking.getDepartureClass(), paxCount);
        if (returnFlight != null) {
            returnFlight.reserveSeats(booking.getReturnClass(), paxCount);
        }
        String bookingReference = bookingReferenceGenerator.generateUniqueReference();
        CreatePaymentCommand paymentData = new CreatePaymentCommand(
                bookingReference,
                dto.getLastFourDigitsCard(),
                dto.getCardHolder(),
                dto.getBillingEmail(),
                dto.getBillingMobile()
        );
        Payment payment = paymentService.createNewPayment(paymentData);
        booking.setReference(bookingReference);
        booking.setPayment(payment);
        booking.setCreatedAt(LocalDateTime.now());

        Booking confirmedBooking = bookingRepository.save(booking);
        return bookingMapper.bookingEntityToConfirmedDTO(confirmedBooking);
    }




}
