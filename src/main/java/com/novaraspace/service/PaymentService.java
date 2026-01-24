package com.novaraspace.service;

import com.novaraspace.model.domain.CreatePaymentCommand;
import com.novaraspace.model.entity.Payment;
import com.novaraspace.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {


    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createNewPayment(CreatePaymentCommand paymentData) {
        boolean noServiceReference = paymentData.serviceReference() == null || paymentData.serviceReference().isBlank();
        String serviceReference = noServiceReference
                ? generateRandomServiceReference()
                : paymentData.serviceReference();

        Payment payment = new Payment()
                .setReference(UUID.randomUUID().toString())
                .setServiceReference(serviceReference)
                .setFirstFour(null)
                .setLastFour(paymentData.lastFour())
                .setCardHolder(paymentData.cardHolder())
                .setCardType(null)
                .setEmail(paymentData.email())
                .setPhoneNumber(paymentData.phoneNumber())
                .setCreatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    private String generateRandomServiceReference() {
        return "RANDOM_REF";
    }
}
