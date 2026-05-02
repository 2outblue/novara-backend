package com.novaraspace.service;

import com.novaraspace.model.domain.UserBookingsQuery;
import com.novaraspace.model.dto.booking.AccountBookingDTO;
import com.novaraspace.model.dto.booking.UserBookingsRequestDTO;
import com.novaraspace.model.dto.payment.PaymentDTO;
import com.novaraspace.model.dto.user.*;
import com.novaraspace.model.entity.Booking;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.entity.UserPaymentCard;
import com.novaraspace.model.entity.VerificationToken;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.audit.PassEventType;
import com.novaraspace.model.events.PasswordEvent;
import com.novaraspace.model.exception.BookingException;
import com.novaraspace.model.exception.UserException;
import com.novaraspace.model.exception.VerificationException;
import com.novaraspace.model.mapper.BookingMapper;
import com.novaraspace.model.mapper.PaymentMapper;
import com.novaraspace.model.mapper.UserMapper;
import com.novaraspace.model.other.PageResponse;
import com.novaraspace.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final PaymentService paymentService;
    private final CurrentUserService currentUserService;
    private final BookingMapper bookingMapper;
    private final PaymentMapper paymentMapper;
    private final ApplicationEventPublisher eventPublisher;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper,
                       PaymentService paymentService,
                       CurrentUserService currentUserService,
                       BookingMapper bookingMapper, PaymentMapper paymentMapper, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.paymentService = paymentService;
        this.currentUserService = currentUserService;
        this.bookingMapper = bookingMapper;
        this.paymentMapper = paymentMapper;
        this.eventPublisher = eventPublisher;
    }

//    @Transactional
//    public void activateUserAccount(String userEmail) {
//        User user = userRepository.findByEmail(userEmail).orElseThrow(VerificationException::failed);
//        user.setStatus(AccountStatus.ACTIVE);
//        user.setVerification(null);
//    }

//    @Transactional
//    public void updateUserVerification(String email, VerificationToken verification) {
//        User user = userRepository.findByEmail(email).orElseThrow(VerificationException::failed);
//        user.setVerification(verification);
//    }

    @Transactional
    public void setLastLoginNow(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Bad credentials."));
        user.setLastLoginAt(Instant.now());
    }

//    public AccountDTO getAccountDTO(String authId) {
//        User user = userRepository.findByAuthId(authId).orElseThrow(UserException::notFound);
//        return userMapper.entityToAccountDTO(user);
//    }

    public AccountDTO getCurrentAccountDTO() {
        User user = currentUserService.getUserEntity().orElseThrow(UserException::notFound);
        return userMapper.entityToAccountDTO(user);
    }

    //TODO: I don't know if this should be here or in the booking service
    @Transactional(readOnly = true)
    public PageResponse<AccountBookingDTO> getCurrentUserBookingsPage(UserBookingsRequestDTO req) {
        User user = currentUserService.getUserEntity().orElseThrow(BookingException::notFound);

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime minDate = req.getTimeFrame().equals("upcoming")
                ? now
                : now.minusMonths(6);
        LocalDateTime maxDate = req.getTimeFrame().equals("upcoming")
                ? now.plusMonths(6)
                : now;

        UserBookingsQuery query = new UserBookingsQuery(
                user.getId(),
                minDate,
                maxDate);
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getSize());
        Page<Booking> page = req.getTimeFrame().equals("upcoming")
                ? userRepository.getUserUpcomingBookings(query, pageable)
                : userRepository.getUserBookingsHistory(query, pageable);
        List<AccountBookingDTO> bookings = page.getContent().stream()
                .map(bookingMapper::entityToAccountBookingDTO)
                .toList();
        return new PageResponse<AccountBookingDTO>()
                .setContent(bookings)
                .setPage(page.getNumber())
                .setSize(page.getSize())
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages());
    }

//    @Transactional(readOnly = true)
//    public PaymentDTO[] getCurrentUserLast10Payments() {
//        User user = currentUserService.getAuthenticatedUser().orElseThrow(UserException::notFound);
//        return user.getPayments().stream()
//                .map(paymentMapper::entityToPaymentDTO).toArray(PaymentDTO[]::new);
//    }

    @Transactional(readOnly = true)
    public UserPaymentsResponseDTO getCurrentUserPaymentsData() {
        User user = currentUserService.getUserEntity().orElseThrow(UserException::notFound);
        PaymentDTO[] payments = user.getPayments().stream()
                .map(paymentMapper::entityToPaymentDTO).toArray(PaymentDTO[]::new);
        return new UserPaymentsResponseDTO()
                .setPayments(payments)
                .setTotalInvoiced(user.getTotalInvoiced());
    }

//    public EmailDTO getEmailByAuthId(String authId) {
//        String email = userRepository.getEmailByAuthId(authId).orElseThrow(UserException::notFound);
//        return new EmailDTO().setEmail(email);
//    }

    @Transactional
    public UserDocumentDTO[] updateUserDocument(UserDocumentUpdateRequest request) {
        User user = currentUserService.getUserEntity().orElseThrow(UserException::updateFailed);
        user.updateDocument(request);

        return user.getDocuments().stream().map(userMapper::documentToDto)
                .toArray(UserDocumentDTO[]::new);
    }

    @Transactional
    public UserCardDTO[] addUserCard(@Valid AddCardDTO dto) {
        User user = currentUserService.getUserEntity().orElseThrow(UserException::updateFailed);

        UserPaymentCard card = paymentService.getUserPaymentCard(dto);
        user.addCard(card);
        return user.getCards().stream().map(userMapper::userCardToDTO)
                .toArray(UserCardDTO[]::new);
    }

    @Transactional
    public UserCardDTO[] removeUserCard(String cardRef) {
        User user = currentUserService.getUserEntity().orElseThrow(UserException::updateFailed);

        user.removeCard(cardRef);
        return user.getCards().stream().map(userMapper::userCardToDTO)
                .toArray(UserCardDTO[]::new);
    }

    @Transactional
    public void changeUserPassword(@Valid PasswordChangeRequestDTO req) {
        User user = currentUserService.getUserEntity().orElseThrow(UserException::notFound);
        if (passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(req.getNewPassword()));
            eventPublisher.publishEvent(new PasswordEvent(PassEventType.CHANGE, user.getEmail()));
            return;
        }
        throw UserException.updateFailed();
    }

    @Transactional
    public UpdatableUserSettingsDTO updateAccountSettings(UpdatableUserSettingsDTO updates) {
        User user = currentUserService.getUserEntity().orElseThrow(UserException::updateFailed);

        userMapper.updateUserSettings(updates, user);
        return updates;
    }

    public User createUser(UserRegisterDTO dto) {
        User newUser = userMapper.registerToUser(dto); //Passwords are hashed in the mapper
        newUser.setAccountNumber(createAccountNumber(newUser.getEmail()));
        return userRepository.save(newUser);
    }

    private String createAccountNumber(String email) {
        byte[] md5Digest = DigestUtils.md5Digest(email.getBytes());
        BigInteger num = new BigInteger(1, md5Digest);
        String base36 = num.toString(36).toUpperCase();
        return base36.length() >= 12 ? base36.substring(0, 12) : base36;
    }

    public Optional<User> findEntityByAuthId(String authId) {return userRepository.findByAuthId(authId);}
//    public Optional<String> getAuthIdByEmail(String email) { return userRepository.getAuthIdByEmail(email);}
    public Optional<User> getEntityByEmail(String email) { return userRepository.findByEmail(email); }

}
