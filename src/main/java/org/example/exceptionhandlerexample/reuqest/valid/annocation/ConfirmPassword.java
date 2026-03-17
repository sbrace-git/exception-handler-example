package org.example.exceptionhandlerexample.reuqest.valid.annocation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.exceptionhandlerexample.reuqest.valid.validator.ConfirmPasswordValidator;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConfirmPasswordValidator.class)
public @interface ConfirmPassword {
    String message() default "Confirm Password Error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
