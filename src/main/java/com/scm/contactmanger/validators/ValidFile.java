package com.scm.contactmanger.validators;

import java.lang.annotation.Documented;
// import java.lang.annotation.Retention;
// import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented

@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile {
    String message() default "Invalid file";

    Class<?>[] groups() default {};

    boolean checkEmpty() default true;

    Class<? extends Payload>[] payload() default {};
}
