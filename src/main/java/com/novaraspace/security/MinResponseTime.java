package com.novaraspace.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Used by ResponseDelayAspect - package com.novaraspace.aspect;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinResponseTime {
    long value() default 350;
}
