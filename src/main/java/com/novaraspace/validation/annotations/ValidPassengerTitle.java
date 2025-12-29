package com.novaraspace.validation.annotations;


import com.novaraspace.validation.validators.ValidPassengerTitleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = ValidPassengerTitleValidator.class)
public @interface ValidPassengerTitle {
    String message() default "Invalid passenger title.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
