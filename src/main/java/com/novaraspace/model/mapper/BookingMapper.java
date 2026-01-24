package com.novaraspace.model.mapper;

import com.novaraspace.model.dto.booking.*;
import com.novaraspace.model.entity.*;
import com.novaraspace.model.enums.CabinClassEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class BookingMapper {

    public abstract BookingQuote bookingQuoteDtoToEntity(BookingQuoteDTO dto);
    public abstract BookingQuoteDTO entityToBookingQuoteDto(BookingQuote entity);

    public abstract BookingConfirmedDTO bookingEntityToConfirmedDTO(Booking entity);

    public Booking newBookingDtoToEntity(NewBookingDTO dto) {
        Booking booking = this.newBookingDtoToPartialEntity(dto);

        NewBookingContactDetails contactDetails = dto.getContactDetails();
        booking.setContactEmail(contactDetails.getContactEmail());
        booking.setContactCountryCode(contactDetails.getContactCountryCode());
        booking.setContactMobile(contactDetails.getContactMobile());

        for (CabinClassEnum enumClass : CabinClassEnum.values()) {
            if (enumClass.getDisplayName().equals(dto.getDepartureFlightClass())) {
                booking.setDepartureClass(enumClass);
            }
            if (enumClass.getDisplayName().equals(dto.getReturnFlightClass())) {
                booking.setReturnClass(enumClass);
            }
        }
        booking.getPassengers().forEach(pax -> pax.setBooking(booking));

        return booking;
    }

    @Mapping(target = "departureFlight", ignore = true)
    @Mapping(target = "returnFlight", ignore = true)
    @Mapping(target = "departureClass", ignore = true)
    @Mapping(target = "returnClass", ignore = true)
    protected abstract Booking newBookingDtoToPartialEntity(NewBookingDTO dto);

    @Mapping(source = "id", target = "intraBookingId")
    @Mapping(target = "id", ignore = true)
    protected abstract Passenger newPassengerDtoToEntity(NewPassengerDTO dto);
    protected abstract ExtraService newExtraServiceDtoToEntity(ExtraServiceDTO dto);
    protected abstract PassengerBaggage newPassengerBaggageDtoToEntity(NewPassengerBaggageDTO dto);
}
