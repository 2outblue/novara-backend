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
import com.novaraspace.model.exception.BookingException;
import com.novaraspace.model.exception.UserException;
import com.novaraspace.model.exception.VerificationException;
import com.novaraspace.model.mapper.BookingMapper;
import com.novaraspace.model.mapper.PaymentMapper;
import com.novaraspace.model.mapper.UserMapper;
import com.novaraspace.model.other.PageResponse;
import com.novaraspace.repository.UserRepository;
import com.novaraspace.util.CountryCodeLoader;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiConsumer;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final PaymentService paymentService;
    private final Map<String, BiConsumer<User, Object>> fieldUpdaters;
    private final Validator validator;
    private final CurrentUserService currentUserService;
    private final BookingMapper bookingMapper;
    private final PaymentMapper paymentMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, PaymentService paymentService, Validator validator, CurrentUserService currentUserService, BookingMapper bookingMapper, PaymentMapper paymentMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.paymentService = paymentService;
        this.validator = validator;
        this.currentUserService = currentUserService;
        this.bookingMapper = bookingMapper;
        this.paymentMapper = paymentMapper;
        this.fieldUpdaters = setFieldUpdaters();
    }

    @Transactional
    public void activateUserAccount(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(VerificationException::failed);
        user.setStatus(AccountStatus.ACTIVE);
        user.setVerification(null);
    }

    @Transactional
    public void updateUserVerification(String email, VerificationToken verification) {
        User user = userRepository.findByEmail(email).orElseThrow(VerificationException::failed);
        user.setVerification(verification);
    }

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
        User user = currentUserService.getAuthenticatedUser().orElseThrow(UserException::notFound);
        return userMapper.entityToAccountDTO(user);
    }

    //TODO: I don't know if this should be here or in the booking service
    @Transactional(readOnly = true)
    public PageResponse<AccountBookingDTO> getCurrentUserBookingsPage(UserBookingsRequestDTO req) {
        User user = currentUserService.getAuthenticatedUser().orElseThrow(BookingException::notFound);

        LocalDateTime minDate = req.getTimeFrame().equals("upcoming")
                ? LocalDateTime.now().plusHours(5)
                : LocalDateTime.now().minusMonths(6);
        LocalDateTime maxDate = req.getTimeFrame().equals("upcoming")
                ? LocalDateTime.now().plusMonths(6)
                : LocalDateTime.now().minusHours(5);

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

    @Transactional(readOnly = true)
    public PaymentDTO[] getCurrentUserLast10Payments() {
        User user = currentUserService.getAuthenticatedUser().orElseThrow(UserException::notFound);
        return user.getPayments().stream()
                .map(paymentMapper::entityToPaymentDTO).toArray(PaymentDTO[]::new);
    }

//    public EmailDTO getEmailByAuthId(String authId) {
//        String email = userRepository.getEmailByAuthId(authId).orElseThrow(UserException::notFound);
//        return new EmailDTO().setEmail(email);
//    }

    @Transactional
    public UserDocumentDTO[] updateUserDocument(UserDocumentUpdateRequest request) {
        User user = currentUserService.getAuthenticatedUser().orElseThrow(UserException::updateFailed);
        user.updateDocument(request);

        return user.getDocuments().stream().map(userMapper::documentToDto)
                .toArray(UserDocumentDTO[]::new);
    }

    @Transactional
    public UserCardDTO[] addUserCard(@Valid AddCardDTO dto) {
        User user = currentUserService.getAuthenticatedUser().orElseThrow(UserException::updateFailed);

        UserPaymentCard card = paymentService.getUserPaymentCard(dto);
        user.addCard(card);
        return user.getCards().stream().map(userMapper::userCardToDTO)
                .toArray(UserCardDTO[]::new);
    }

    @Transactional
    public UserCardDTO[] removeUserCard(String cardRef) {
        if (cardRef.length() > 20 || cardRef.length() < 10) { throw UserException.updateFailed(); }
        User user = currentUserService.getAuthenticatedUser().orElseThrow(UserException::updateFailed);

        user.removeCard(cardRef);
        return user.getCards().stream().map(userMapper::userCardToDTO)
                .toArray(UserCardDTO[]::new);
    }


    @Transactional
    //TODO Definitely move most of the logic for this to a component or in the domain!
    // And rename the dto and methods to something to do with settings?
    // and probably replace all fieldUpdateFailed exceptions with just updateFailed
    public List<UpdateFieldDTO> updateFields(List<UpdateFieldDTO> updates) {
        if (updates == null || updates.isEmpty()) {throw UserException.fieldUpdateFailed();}

        User user = currentUserService.getAuthenticatedUser().orElseThrow(UserException::fieldUpdateFailed);
        for (UpdateFieldDTO dto : updates) {
            if (!dto.getOldValue().getClass().equals(dto.getNewValue().getClass())) {
                throw UserException.fieldUpdateFailed();
            }
            BiConsumer<User, Object> updater = fieldUpdaters.get(dto.getFieldName());
            updater.accept(user, dto.getNewValue());
        }

        UserRegisterDTO registerDTO = userMapper.userToRegisterDTO(user);
        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(registerDTO); // Validate updates based on registerDTO constraints
        updates.forEach(el -> {
            violations.removeIf(v -> !el.getFieldName().equals(v.getPropertyPath().toString()));
        });
        if (!violations.isEmpty()) {
            throw UserException.fieldUpdateFailed();
        }
        return updates;
    }


//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email)
//                .map(this::entityToUserDetails)
//                .orElseThrow(() -> new BadCredentialsException("Bad credentials."));
//    }
//
//    private UserDetails entityToUserDetails(User entity) {
//        boolean isActive = entity.getStatus() == AccountStatus.ACTIVE;
//        return new org.springframework.security.core.userdetails.User(
//                entity.getEmail(),
//                entity.getPassword(),
//                isActive,
//                true,
//                true,
//                true,
//                entity.getRoles().stream()
//                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
//                        .collect(Collectors.toList())
//        );
//    }

//    public User persistUser(User user) {
//        return userRepository.save(user);
//    }

    public User createUser(UserRegisterDTO dto) {
        User newUser = userMapper.registerToUser(dto); //Passwords are hashed in the mapper
        return userRepository.save(newUser);
    }


    public Optional<User> findEntityByAuthId(String authId) {return userRepository.findByAuthId(authId);}
//    public Optional<String> getAuthIdByEmail(String email) { return userRepository.getAuthIdByEmail(email);}
    public Optional<User> getEntityByEmail(String email) { return userRepository.findByEmail(email); }

    //TODO Can you replace the BiConsumer with a func that returns a boolean? this way you can just throw once
    private Map<String, BiConsumer<User, Object>> setFieldUpdaters() {
        return Map.of(
                "countryCode", (u, v) -> {
                    if (v instanceof String) {u.setCountryCode((String) v);}
                    else {throw UserException.fieldUpdateFailed();}
                },
                "phoneNumber", (u, v) -> {
                    if (v instanceof String) {u.setPhoneNumber((String) v);}
                    else if (v instanceof Integer) {u.setPhoneNumber(Integer.toString((Integer)v));}
                    else {throw UserException.fieldUpdateFailed();}
                },
                "addressLine1", (u, v) -> {
                    if (v instanceof String) {u.setAddressLine1((String) v);}
                    else {throw UserException.fieldUpdateFailed();}
                },
                "addressLine2", (u, v) -> {
                    if (v instanceof String) {u.setAddressLine2((String) v);}
                    else {throw UserException.fieldUpdateFailed();}
                },
                "country", (u, v) -> {
                    if (v instanceof String) {u.setCountry((String) v);}
                    else {throw UserException.fieldUpdateFailed();}
                },
                "newsletter", (u, v) -> {
                    if (v instanceof Boolean) {u.setNewsletter((Boolean) v);}
                    else {throw UserException.fieldUpdateFailed();}
                },
                "marketing", (u, v) -> {
                    if (v instanceof Boolean) {u.setMarketing((Boolean) v);}
                    else {throw UserException.fieldUpdateFailed();}
                }
        );
    }
}
