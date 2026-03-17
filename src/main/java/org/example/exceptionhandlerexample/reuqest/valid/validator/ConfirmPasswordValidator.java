package org.example.exceptionhandlerexample.reuqest.valid.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.Strings;
import org.example.exceptionhandlerexample.reuqest.user.UserRequest;
import org.example.exceptionhandlerexample.reuqest.valid.annocation.ConfirmPassword;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, UserRequest> {
    @Override
    public boolean isValid(UserRequest userRequest, ConstraintValidatorContext context) {
        return Strings.CS.equals(userRequest.getPassword(), userRequest.getConfirmPassword());
    }
}