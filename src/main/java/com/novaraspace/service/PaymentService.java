package com.novaraspace.service;

import com.novaraspace.model.domain.CreatePaymentCommand;
import com.novaraspace.model.dto.payment.NewPaymentDTO;
import com.novaraspace.model.entity.Payment;
import com.novaraspace.model.mapper.PaymentMapper;
import com.novaraspace.repository.PaymentRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {


    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public Payment createNewPayment(@Valid NewPaymentDTO dto) {
        Payment payment = createPaymentFromNewPaymentDTO(dto);
        payment.setServiceReference(generateRandomServiceReference());
        return paymentRepository.save(payment);
    }

    public Payment createNewPayment(@Valid NewPaymentDTO dto, String serviceReference) {
        boolean invalidServiceReference = serviceReference == null || serviceReference.isBlank();
        String validServiceReference = invalidServiceReference
                ? generateRandomServiceReference()
                : serviceReference;

        Payment payment = createPaymentFromNewPaymentDTO(dto);
        payment.setServiceReference(validServiceReference);
        return paymentRepository.save(payment);
    }


    //TODO: Implement
    private String generateRandomServiceReference() {
        return "RANDOM_REF";
    }

    /**
     * Doesn't set the serviceReference
     */
    private Payment createPaymentFromNewPaymentDTO(NewPaymentDTO dto) {
        Payment partialEntity = paymentMapper.newPaymentDTOToEntity(dto);
        return partialEntity
                .setReference(UUID.randomUUID().toString())
                .setCardType(determineCardType(partialEntity.getFirstFour()))
                .setCreatedAt(LocalDateTime.now());
    }

    private String determineCardType(String firstFourDigits) {
        if (firstFourDigits.length() != 4) {return "UNKNOWN";}
        int prefix = Integer.parseInt(firstFourDigits);

        if (firstFourDigits.startsWith("4")) {
            return "VISA";
        }
        if (prefix >= 5100 && prefix <= 5599) {
            return "MASTERCARD";
        }
        if (prefix >= 2221 && prefix <= 2720) {
            return "MASTERCARD";
        }
        if (firstFourDigits.startsWith("34") || firstFourDigits.startsWith("37")) {
            return "AMERICAN_EXPRESS";
        }

        return "UNKNOWN";
    }
}
