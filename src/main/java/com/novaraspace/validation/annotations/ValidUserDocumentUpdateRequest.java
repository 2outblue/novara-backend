package com.novaraspace.validation.annotations;


import com.novaraspace.validation.validators.UserDocumentUpdateRequestValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = UserDocumentUpdateRequestValidator.class)
public @interface ValidUserDocumentUpdateRequest {
    String message() default "Invalid user document update request";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
