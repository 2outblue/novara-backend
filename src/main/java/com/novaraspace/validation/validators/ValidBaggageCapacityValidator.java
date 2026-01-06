package com.novaraspace.validation.validators;

import com.novaraspace.validation.annotations.ValidBaggageCapacity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValidBaggageCapacityValidator implements ConstraintValidator<ValidBaggageCapacity, String> {

    private final Set<String> validCapacities = new HashSet<>(Arrays.asList("5kg", "10kg", "15kg", "20kg", "25kg", "30kg", "35kg", "40kg", "45kg", "50kg"));

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return validCapacities.contains(value);
    }
}
