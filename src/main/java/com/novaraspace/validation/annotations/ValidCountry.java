package com.novaraspace.validation.annotations;

import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target(FIELD)
@Retention(RUNTIME)
public @interface ValidCountry {
    String message() default "Invalid country.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
