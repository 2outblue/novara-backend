package com.novaraspace.validation.validators;


import com.novaraspace.util.CountryCodeLoader;
import com.novaraspace.validation.annotations.ValidCountryCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidCountryCodeValidator implements ConstraintValidator<ValidCountryCode, String> {
    private List<String> validDialCodes;
    private final CountryCodeLoader countryCodeLoader;

    public ValidCountryCodeValidator(CountryCodeLoader countryCodeLoader) {
        this.countryCodeLoader = countryCodeLoader;
    }

    @Override
    public void initialize(ValidCountryCode constraintAnnotation) {
        this.validDialCodes = countryCodeLoader.getDialCodes();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validDialCodes.stream().anyMatch(code -> code.equals(value));
    }
}
