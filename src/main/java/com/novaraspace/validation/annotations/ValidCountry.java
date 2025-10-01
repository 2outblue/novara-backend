package com.novaraspace.validation.annotations;

import com.novaraspace.validation.validators.ValidCountryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = ValidCountryValidator.class)
public @interface ValidCountry {
    String message() default "Invalid country.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
