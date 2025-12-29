package com.novaraspace.model.mapper;

import com.novaraspace.model.dto.booking.NewBookingContactDetails;
import com.novaraspace.model.dto.booking.NewBookingDTO;
import com.novaraspace.model.entity.Booking;
import com.novaraspace.model.entity.ExtraService;
import com.novaraspace.model.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class BookingMapper {

    public Booking newBookingDtoToEntity(NewBookingDTO dto) {
        Booking booking = this.newBookingDtoToPartialEntity(dto);

        NewBookingContactDetails contactDetails = dto.getContactDetails();
        booking.setContactEmail(contactDetails.getContactEmail());
        booking.setContactCountryCode(contactDetails.getContactCountryCode());
        booking.setContactMobile(contactDetails.getContactMobile());
        return booking;
    }

    @Mapping(target = "departureFlight", ignore = true)
    @Mapping(target = "returnFlight", ignore = true)
    protected abstract Booking newBookingDtoToPartialEntity(NewBookingDTO dto);

    protected abstract Passenger newPassengerDtoToEntity(Passenger passenger);

    protected abstract ExtraService newExtraServiceDtoToEntity(ExtraService extraService);
}
