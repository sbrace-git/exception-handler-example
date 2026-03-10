package org.example.exceptionhandlerexample.component;

import org.example.exceptionhandlerexample.response.ProblemDetails;
import org.jspecify.annotations.Nullable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ProblemDetails createProblemDetail(Exception ex, HttpStatusCode status, String defaultDetail, @Nullable String detailMessageCode, Object @Nullable [] detailMessageArguments, WebRequest request) {
        ErrorResponse.Builder builder = ErrorResponse.builder(ex, status, defaultDetail);
        if (detailMessageCode != null) {
            builder.detailMessageCode(detailMessageCode);
        }

        if (detailMessageArguments != null) {
            builder.detailMessageArguments(detailMessageArguments);
        }

        ProblemDetail problemDetail = builder.build().updateAndGetBody(getMessageSource(), LocaleContextHolder.getLocale());
        return new ProblemDetails(problemDetail);
    }
}
