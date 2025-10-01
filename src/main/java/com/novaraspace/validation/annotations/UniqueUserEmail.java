package com.novaraspace.validation.annotations;

import com.nimbusds.jose.Payload;
import com.novaraspace.validation.validators.UniqueUserEmailValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueUserEmailValidator.class)
public @interface UniqueUserEmail {
    String message() default "Email already exists.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
