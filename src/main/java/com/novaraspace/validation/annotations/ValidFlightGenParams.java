package com.novaraspace.validation.annotations;


import com.novaraspace.validation.validators.FlightGenParamsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = FlightGenParamsValidator.class)
public @interface ValidFlightGenParams {
    String message() default "Invalid flight generation params";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
