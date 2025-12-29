package com.novaraspace.validation.validators;

import com.novaraspace.validation.annotations.ValidPassengerAgeGroup;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValidPassengerAgeGroupValidator implements ConstraintValidator<ValidPassengerAgeGroup, String> {

    private final Set<String> validAgeGroups = new HashSet<>(Arrays.asList("junior", "adult"));

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validAgeGroups.contains(value);
    }
}
