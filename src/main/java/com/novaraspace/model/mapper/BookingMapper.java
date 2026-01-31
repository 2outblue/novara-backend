package com.novaraspace.model.mapper;

import com.novaraspace.model.dto.booking.*;
import com.novaraspace.model.dto.flight.BookedFlightDTO;
import com.novaraspace.model.entity.*;
import com.novaraspace.model.enums.CabinClassEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = FlightMapper.class
)
public abstract class BookingMapper {
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
    protected abstract Passenger newPassengerDtoToEntity(PassengerDTO dto);
    @Mapping(source = "intraBookingId", target = "id")
    protected abstract PassengerDTO passengerEntityToDTO(Passenger entity);
    protected abstract ExtraService newExtraServiceDtoToEntity(ExtraServiceDTO dto);
    protected abstract ExtraServiceDTO extraServiceEntityToDTO(ExtraService entity);
    protected abstract PassengerBaggage newPassengerBaggageDtoToEntity(PassengerBaggageDTO dto);
    protected abstract PassengerBaggageDTO passengerBaggageEntityToDTO(PassengerBaggage entity);




    public BookingDTO entityToDTO(Booking entity) {
        BookingDTO dto = entityToPartialDTO(entity);

        BookedFlightDTO departureFl = dto.getDepartureFlight();
        BookedFlightDTO returnFl = dto.getReturnFlight();

        departureFl.setSelectedClass(entity.getDepartureClass().getDisplayName());
        departureFl.setPrice(entity.getDepartureFlightPrice());
        if (returnFl != null) {
            returnFl.setSelectedClass(entity.getReturnClass().getDisplayName());
            returnFl.setPrice(entity.getReturnFlightPrice());
        }

        boolean oneWay = entity.getReturnFlight() == null;
        dto.setOneWay(oneWay);
        return dto;
    }
    protected abstract BookingDTO entityToPartialDTO(Booking entity);

}
