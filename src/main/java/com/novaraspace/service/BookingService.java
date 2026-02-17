package com.novaraspace.service;

import com.novaraspace.component.BookingReferenceGenerator;
import com.novaraspace.component.DataMasker;
import com.novaraspace.model.dto.booking.*;
import com.novaraspace.model.dto.flight.FlightSearchQueryDTO;
import com.novaraspace.model.dto.flight.FlightSearchResultDTO;
import com.novaraspace.model.dto.flight.FlightUiDTO;
import com.novaraspace.model.entity.Booking;
import com.novaraspace.model.entity.FlightInstance;
import com.novaraspace.model.entity.Passenger;
import com.novaraspace.model.entity.Payment;
import com.novaraspace.model.exception.BookingException;
import com.novaraspace.model.mapper.BookingMapper;
import com.novaraspace.repository.BookingRepository;
import com.novaraspace.validation.business.BookingValidator;
//import jakarta.transaction.Transactional;
import com.novaraspace.validation.business.ChangeFlightValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {


    private final BookingMapper bookingMapper;
    private final FlightService flightService;
    private final BookingQuoteService bookingQuoteService;
    private final BookingRepository bookingRepository;
    private final BookingValidator bookingValidator;
    private final BookingReferenceGenerator bookingReferenceGenerator;
    private final PaymentService paymentService;
    private final DataMasker dataMasker;
    private final ChangeFlightValidator changeFlightValidator;

    //TODO: Maybe rename this to something like BookingCreationService and keep the getResultForNewBookingStart
    // and createNewBooking methods, and move the others to a BookingManageService
    public BookingService(
            BookingMapper bookingMapper,
            FlightService flightService,
            BookingQuoteService bookingQuoteService,
            BookingRepository bookingRepository,
            BookingValidator bookingValidator,
            BookingReferenceGenerator bookingReferenceGenerator,
            PaymentService paymentService,
            DataMasker dataMasker, ChangeFlightValidator changeFlightValidator) {
        this.bookingMapper = bookingMapper;
        this.flightService = flightService;
        this.bookingQuoteService = bookingQuoteService;
        this.bookingRepository = bookingRepository;
        this.bookingValidator = bookingValidator;
        this.bookingReferenceGenerator = bookingReferenceGenerator;
        this.paymentService = paymentService;
        this.dataMasker = dataMasker;
        this.changeFlightValidator = changeFlightValidator;
    }

    public BookingStartResultDTO getResultForNewBookingStart(FlightSearchQueryDTO flightSearchQueryDTO) {
        FlightSearchResultDTO flightSearchResultDTO = flightService.getFlightSearchResult(flightSearchQueryDTO);
        BookingQuoteDTO bookingQuote = bookingQuoteService.createNewQuote(flightSearchQueryDTO, flightSearchResultDTO);

        return new BookingStartResultDTO()
                .setFlightSearchResult(flightSearchResultDTO)
                .setQuoteRef(bookingQuote.getReference());
    }

    @Transactional
    public BookingConfirmedDTO createNewBooking(BookingCreateRequest request) {
        NewBookingDTO bookingDTO = request.getBookingDTO();
        Booking booking = bookingMapper.newBookingDtoToEntity(bookingDTO);

        FlightInstance departureFlight = flightService.findFlightByPublicId(bookingDTO.getDepartureFlightId())
                .orElseThrow(BookingException::creationFailed);
        FlightInstance returnFlight = flightService.findFlightByPublicId(bookingDTO.getReturnFlightId())
                .orElse(null);
        booking.setDepartureFlight(departureFlight);
        booking.setReturnFlight(returnFlight);

        String bookingReference = bookingReferenceGenerator.generateUniqueReference();
        Payment payment = paymentService.createNewPayment(request.getPaymentDTO(), bookingReference);

        boolean validBooking = bookingValidator.validateNewBooking(booking, bookingDTO.getQuoteReference());
        boolean validBookingPayment = bookingValidator.validateBookingAgainstPayment(booking, payment);
        if (!validBooking || !validBookingPayment) {throw BookingException.creationFailed();}

        int paxCount = booking.getPassengers().size();
        departureFlight.reserveSeats(booking.getDepartureClass(), paxCount);
        if (returnFlight != null) {
            returnFlight.reserveSeats(booking.getReturnClass(), paxCount);
        }
        booking.setReference(bookingReference);
        booking.setPayment(payment);
        booking.setCreatedAt(LocalDateTime.now());

        //TODO: Maybe normalize the prices before saving the booking ? If you do - normalize the payment as well.
        Booking confirmedBooking = bookingRepository.save(booking);
        return bookingMapper.bookingEntityToConfirmedDTO(confirmedBooking);
    }

    @Transactional(readOnly = true)
    public BookingDTO findBooking(BookingSearchParams params) {
        Booking booking = findValidBooking(params);

        BookingDTO dto = bookingMapper.entityToDTO(booking);
        String maskedEmail = dataMasker.maskEmail(booking.getContactEmail());
        String maskedMobile = dataMasker.maskPhoneNumber(booking.getContactMobile());

        dto.setContactEmailMasked(maskedEmail)
                .setContactMobileMasked(maskedMobile);

        return dto;
    }

    @Transactional
    public BookingDTO cancelBooking(BookingSearchParams params) {
        //TODO: Maybe make 'reverse' payments of refundable amount to show them as refunds on user account?
        Booking booking = findValidBooking(params);
        FlightInstance departureFlight = booking.getDepartureFlight();
        if (!departureFlight.departsAtLeast3HoursFromNow() || booking.isCancelled()) {
            throw BookingException.changeFailed();
        }
        booking.cancel();
        Booking cancelledBooking = bookingRepository.save(booking);
        return bookingMapper.entityToDTO(cancelledBooking);
    }

    @Transactional
    public ChangeFlightsStartResponse getResultForFlightChangeStart(FlightSearchQueryDTO queryDTO) {
        FlightSearchResultDTO flightSearchResultDTO = flightService.getFlightSearchResult(queryDTO);
        BookingQuoteDTO bookingQuote = bookingQuoteService.createNewQuote(queryDTO, flightSearchResultDTO);

        return new ChangeFlightsStartResponse()
                .setFlightSearchResult(flightSearchResultDTO)
                .setQuoteReference(bookingQuote.getReference());
    }

    @Transactional
    public BookingDTO changeFlights(ChangeFlightsRequest request) {
        Booking booking = findValidBooking(request.getBookingParams());
        boolean validChange = changeFlightValidator.validateFlightChange(booking, request);
        if (!validChange) {throw BookingException.changeFailed();}

        String newDepFlightPublicId = request.getDepartureFlight().getId();
        FlightInstance newDepartureFlight = flightService.findFlightByPublicId(newDepFlightPublicId)
                .orElseThrow(BookingException::changeFailed);
        booking.changeDepartureFlight(newDepartureFlight, request.getDepartureFlight());

        if (request.getReturnFlight() != null && booking.getReturnFlight() != null) {
            String newRetFlightPublicId = request.getReturnFlight().getId();
            FlightInstance newRetFlight = flightService.findFlightByPublicId(newRetFlightPublicId)
                    .orElseThrow(BookingException::changeFailed);
            booking.changeReturnFlight(newRetFlight, request.getReturnFlight());
        }
        //TODO: Normalize prices?
        Payment payment = paymentService.createNewPayment(request.getPayment(), booking.getReference());
        Booking changedBooking = bookingRepository.save(booking);
        return bookingMapper.entityToDTO(changedBooking);
    }


    private Booking findValidBooking(BookingSearchParams params) {
        Booking booking = bookingRepository
                .findByReference(params.getReference())
                .orElseThrow(BookingException::notFound);

        List<Passenger> passengers = booking.getPassengers();
        String lastName = params.getLastName();
        boolean lastNameIsOneOfThePassengers =  passengers.stream()
                .anyMatch(pax -> pax.getLastName().equals(lastName));
        if (!lastNameIsOneOfThePassengers) { throw BookingException.notFound(); }
        return booking;
    }


}
