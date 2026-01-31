package com.novaraspace.validation.annotations;

import com.novaraspace.validation.validators.ValidReturnFlightComponentsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = ValidReturnFlightComponentsValidator.class)
public @interface ValidReturnFlightComponents {
    String message() default "Invalid return flight data";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
