package com.novaraspace.service;

import com.novaraspace.model.dto.payment.NewPaymentDTO;
import com.novaraspace.model.dto.user.AddCardDTO;
import com.novaraspace.model.entity.Payment;
import com.novaraspace.model.entity.UserPaymentCard;
import com.novaraspace.model.mapper.PaymentMapper;
import com.novaraspace.repository.PaymentRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {


    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public Payment createBookingConfirmPayment(@Valid NewPaymentDTO dto, String bookingReference) {
        Payment payment = createPaymentEntity(dto, bookingReference);
        payment.setBookingConfirm(true);
        return paymentRepository.save(payment);
    }

    public Payment createNewPayment(@Valid NewPaymentDTO dto, String serviceReference) {
        Payment payment = createPaymentEntity(dto, serviceReference);
        return paymentRepository.save(payment);
    }

    private Payment createPaymentEntity(NewPaymentDTO dto, String serviceReference) {
        boolean invalidServiceReference = serviceReference == null || serviceReference.isBlank();
        String validServiceReference = invalidServiceReference
                ? generateRandomServiceReference()
                : serviceReference;

        Payment payment = paymentMapper.newPaymentDTOToEntity(dto);
        payment.setReference(UUID.randomUUID().toString())
                .setCardType(determineCardType(payment.getFirstFour()))
                .setCreatedAt(LocalDateTime.now())
                .setServiceReference(validServiceReference);

        return payment;
    }

    public UserPaymentCard getUserPaymentCard(@Valid AddCardDTO dto) {
        BytesKeyGenerator generator = KeyGenerators.secureRandom(14);
        String ref = Base64.getUrlEncoder().withoutPadding().encodeToString(generator.generateKey());
        String cardType = determineCardType(dto.getFirstFour());

        return new UserPaymentCard()
                .setReference(ref)
                .setFirstFour(dto.getFirstFour())
                .setLastFour(dto.getLastFour())
                .setCardHolder(dto.getCardHolder())
                .setCardType(cardType)
                .setExpiryDate(dto.getExpiryDate())
                .setAddedOn(LocalDate.now());
    }


    private String generateRandomServiceReference() {
        String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new SecureRandom();
        StringBuilder code = new StringBuilder(13);
        for (int i = 0; i < 13; i++) {
            code.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return code.toString();
    }

    /**
     * Doesn't set the serviceReference
     */
//    private Payment createPaymentFromNewPaymentDTO(NewPaymentDTO dto) {
//        Payment partialEntity = paymentMapper.newPaymentDTOToEntity(dto);
//        return partialEntity
//                .setReference(UUID.randomUUID().toString())
//                .setCardType(determineCardType(partialEntity.getFirstFour()))
//                .setCreatedAt(LocalDateTime.now());
//    }

    private String determineCardType(String firstFourDigits) {
        if (firstFourDigits.length() != 4) {return "Card";}
        int prefix = Integer.parseInt(firstFourDigits);

        if (firstFourDigits.startsWith("4")) {
            return "Visa";
        }
//        if (prefix >= 5100 && prefix <= 5599) {
//            return "MASTERCARD";
//        }
        if (firstFourDigits.startsWith("5") || firstFourDigits.startsWith("2")) {
            return "Mastercard";
        }
//        if (prefix >= 2221 && prefix <= 2720) {
//            return "MASTERCARD";
//        }
//        if (firstFourDigits.startsWith("34") || firstFourDigits.startsWith("37")) {
//            return "AMERICAN_EXPRESS";
//        }
        if (firstFourDigits.startsWith("35")) {
            return "JCB";
        }
        if (firstFourDigits.startsWith("3")) {
            return "Amex";
        }
        if (firstFourDigits.startsWith("6")) {
            return "Discover";
        }

        return "Card";
    }
}
