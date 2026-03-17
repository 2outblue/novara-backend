package com.novaraspace.model.dto.user;

import com.novaraspace.model.dto.payment.PaymentDTO;

public class UserPaymentsResponseDTO {
    private PaymentDTO[] payments;
    private double totalInvoiced;

    public PaymentDTO[] getPayments() {
        return payments;
    }

    public UserPaymentsResponseDTO setPayments(PaymentDTO[] payments) {
        this.payments = payments;
        return this;
    }

    public double getTotalInvoiced() {
        return totalInvoiced;
    }

    public UserPaymentsResponseDTO setTotalInvoiced(double totalInvoiced) {
        this.totalInvoiced = totalInvoiced;
        return this;
    }
}
