package com.novaraspace.model.dto.booking;

import com.novaraspace.model.dto.payment.NewPaymentDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class BookingCreateRequest {
    @NotNull
    @Valid
    private NewBookingDTO bookingDTO;
    @NotNull
    @Valid
    private NewPaymentDTO paymentDTO;

    public NewBookingDTO getBookingDTO() {
        return bookingDTO;
    }

    public BookingCreateRequest setBookingDTO(NewBookingDTO bookingDTO) {
        this.bookingDTO = bookingDTO;
        return this;
    }

    public NewPaymentDTO getPaymentDTO() {
        return paymentDTO;
    }

    public BookingCreateRequest setPaymentDTO(NewPaymentDTO paymentDTO) {
        this.paymentDTO = paymentDTO;
        return this;
    }
}
