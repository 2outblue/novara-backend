package com.novaraspace.validation.validators;

import com.novaraspace.validation.annotations.ValidUserTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValidUserTitleValidator implements ConstraintValidator<ValidUserTitle, String> {

    private final Set<String> validTitles = new HashSet<>(Arrays.asList("Mr", "Mrs", "Miss", "Ms", "Other"));

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validTitles.contains(value);
    }
}
