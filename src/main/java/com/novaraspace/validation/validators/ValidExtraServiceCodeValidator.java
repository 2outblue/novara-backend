package com.novaraspace.validation.validators;

import com.novaraspace.validation.annotations.ValidExtraServiceCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValidExtraServiceCodeValidator implements ConstraintValidator<ValidExtraServiceCode, String> {

    private final Set<String> validCodes = new HashSet<>(Arrays.asList("priority", "shared", "baggage", "insurance"));

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validCodes.contains(value);
    }
}
