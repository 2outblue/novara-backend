package com.novaraspace.validation.validators;

import com.novaraspace.util.CountryCodeLoader;
import com.novaraspace.validation.annotations.ValidCountry;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidCountryValidator implements ConstraintValidator<ValidCountry, String> {
    private List<String> validCountries;
    private final CountryCodeLoader countryCodeLoader;

    public ValidCountryValidator(CountryCodeLoader countryCodeLoader) {
        this.countryCodeLoader = countryCodeLoader;
    }

    @Override
    public void initialize(ValidCountry constraintAnnotation) {
        this.validCountries = countryCodeLoader.getCountryNames();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validCountries.stream().anyMatch(country -> country.equals(value));
    }
}
