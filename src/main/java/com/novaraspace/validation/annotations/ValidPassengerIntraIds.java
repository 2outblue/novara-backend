package com.novaraspace.validation.annotations;

import com.novaraspace.validation.validators.PassengerIntraIdsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = PassengerIntraIdsValidator.class)
public @interface ValidPassengerIntraIds {
    String message() default "Invalid passenger intra booking ID or cabin ID";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
