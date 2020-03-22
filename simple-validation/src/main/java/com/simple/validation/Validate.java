package com.simple.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validate annotation, mark the class or field should be validate
 *
 * @author liam
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {

    /**
     * the order of field validator
     *
     * @return
     */
    int order() default 0;
}
