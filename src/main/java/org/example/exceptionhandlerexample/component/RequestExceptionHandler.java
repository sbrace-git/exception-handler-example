package org.example.exceptionhandlerexample.component;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptionhandlerexample.response.ParamError;
import org.example.exceptionhandlerexample.response.ProblemDetails;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ParamError> paramErrorList = ex.getBindingResult().getAllErrors().stream().map(ParamError::new).toList();
        ProblemDetails problemDetails = new ProblemDetails(ex.getBody());
        problemDetails.setErrors(paramErrorList);
        return handleExceptionInternal(ex, problemDetails, headers, status, request);
    }

}
