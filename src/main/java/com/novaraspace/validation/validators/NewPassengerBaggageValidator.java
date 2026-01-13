package com.novaraspace.validation.validators;

import com.novaraspace.model.dto.booking.NewPassengerBaggageDTO;
import com.novaraspace.validation.annotations.ValidNewPassengerBaggage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NewPassengerBaggageValidator implements ConstraintValidator<ValidNewPassengerBaggage, NewPassengerBaggageDTO> {
    private final Set<String> validCapacities = new HashSet<>(Arrays.asList(
            "5kg", "10kg", "15kg", "20kg", "25kg", "30kg", "35kg", "40kg", "45kg", "50kg"
    ));

    @Override
    public boolean isValid(NewPassengerBaggageDTO dto, ConstraintValidatorContext context) {
        String capacity = dto.getCapacity();
        if (capacity == null) {return false;}

        boolean noBaggage = capacity.isEmpty() && dto.getPrice() == 0;
        boolean baggageIsValid =
                validCapacities.contains(capacity)
                && dto.getPrice() > 0;

        return noBaggage || baggageIsValid;
    }
}
