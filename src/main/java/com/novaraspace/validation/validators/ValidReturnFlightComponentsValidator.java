package com.novaraspace.validation.validators;

import com.novaraspace.model.dto.booking.NewBookingDTO;
import com.novaraspace.validation.annotations.ValidReturnFlightComponents;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidReturnFlightComponentsValidator implements ConstraintValidator<ValidReturnFlightComponents, NewBookingDTO> {

    @Override
    public boolean isValid(NewBookingDTO dto, ConstraintValidatorContext context) {
        String returnFlightId = dto.getReturnFlightId();
        boolean noReturnFlight = returnFlightId == null || returnFlightId.isBlank();
        boolean noReturnClass = dto.getReturnFlightClass() == null;
        boolean noReturnPrice = dto.getReturnFlightPrice() == null || dto.getReturnFlightPrice() == 0.0;

        if (noReturnFlight) {
            return noReturnPrice && noReturnClass;
        }
        return !noReturnPrice && !noReturnClass;
    }
}
