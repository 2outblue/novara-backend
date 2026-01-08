package com.novaraspace.service;

import com.novaraspace.model.dto.booking.NewBookingDTO;
import com.novaraspace.model.entity.Booking;
import com.novaraspace.model.mapper.BookingMapper;
import org.springframework.stereotype.Service;

@Service
public class BookingService {


    private final BookingMapper bookingMapper;

    public BookingService(BookingMapper bookingMapper) {
        this.bookingMapper = bookingMapper;
    }

    public void createNewBooking(NewBookingDTO dto) {
        Booking booking = bookingMapper.newBookingDtoToEntity(dto);
        System.out.println(booking);
    }

    private void validateBooking(Booking booking) {

    }




}
