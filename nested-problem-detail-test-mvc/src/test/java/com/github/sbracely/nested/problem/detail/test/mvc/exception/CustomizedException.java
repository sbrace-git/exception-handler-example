package com.github.sbracely.nested.problem.detail.test.mvc.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

public class CustomizedException extends ErrorResponseException {

    public CustomizedException(HttpStatusCode status, ProblemDetail body, @Nullable Throwable cause) {
        super(status, body, cause);
    }
}
