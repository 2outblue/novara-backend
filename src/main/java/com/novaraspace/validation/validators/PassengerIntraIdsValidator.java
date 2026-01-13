package com.novaraspace.validation.validators;

import com.novaraspace.model.dto.booking.NewBookingDTO;
import com.novaraspace.model.dto.booking.NewPassengerDTO;
import com.novaraspace.validation.annotations.ValidPassengerIntraIds;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PassengerIntraIdsValidator implements ConstraintValidator<ValidPassengerIntraIds, NewBookingDTO> {

    @Override
    public boolean isValid(NewBookingDTO bookingDTO, ConstraintValidatorContext context) {
        List<NewPassengerDTO> passengers = bookingDTO.getPassengers();
        if (passengers.isEmpty()) {return false;}
        Set<Long> intraBookingIds = passengers.stream().map(NewPassengerDTO::getId).collect(Collectors.toSet());
        Set<Long> cabinIds = passengers.stream().map(NewPassengerDTO::getCabinId).collect(Collectors.toSet());

        boolean allPaxHaveUniqueIntraId = intraBookingIds.size() == passengers.size();
        boolean allPaxAreInExistingCabins = intraBookingIds.containsAll(cabinIds);
        boolean atLeastOneOwnerPax = passengers.stream().anyMatch(pax -> pax.getId().equals(pax.getCabinId()));

        return allPaxHaveUniqueIntraId && allPaxAreInExistingCabins && atLeastOneOwnerPax;
    }
}
