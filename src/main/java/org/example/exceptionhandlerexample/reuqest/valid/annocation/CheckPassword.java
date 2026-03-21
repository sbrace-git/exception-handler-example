package org.example.exceptionhandlerexample.reuqest.valid.annocation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.exceptionhandlerexample.reuqest.valid.validator.CheckPasswordValidator;

import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckPasswordValidator.class)
public @interface CheckPassword {
    String message() default "Check password error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
