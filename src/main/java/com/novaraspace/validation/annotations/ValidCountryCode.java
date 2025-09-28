package com.novaraspace.validation.annotations;


import com.novaraspace.validation.validators.ValidCountryCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = ValidCountryCodeValidator.class)
public @interface ValidCountryCode {
    String message() default "Invalid country code.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
