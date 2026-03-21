package org.example.exceptionhandlerexample.reuqest.valid.annocation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.exceptionhandlerexample.reuqest.valid.validator.CheckMultipartFileValidator;

import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckMultipartFileValidator.class)
public @interface CheckMultipartFile {
    String message() default "Check file error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] extensionInclude() default {};

    String extensionIncludeMessage() default "Extension not support";

    boolean required() default true;

    String requiredMessage() default "File required";
}
