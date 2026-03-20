package org.example.exceptionhandlerexample.component;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptionhandlerexample.response.ErrorCode;
import org.example.exceptionhandlerexample.response.ParamError;
import org.example.exceptionhandlerexample.response.ParamErrorType;
import org.example.exceptionhandlerexample.response.NestedProblemDetail;
import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.ParameterErrors;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ParamError> paramErrorList = ex.getBindingResult().getAllErrors().stream().map(ParamError::new).toList();
        NestedProblemDetail nestedProblemDetail = new NestedProblemDetail(ex.getBody());
        nestedProblemDetail.setErrors(paramErrorList);
        return handleExceptionInternal(ex, nestedProblemDetail, headers, status, request);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ParamError> paramErrorList = new ArrayList<>();
        ex.visitResults(new HandlerMethodValidationException.Visitor() {

            @Override
            public void cookieValue(CookieValue cookieValue, ParameterValidationResult result) {
                String parameterName = result.getMethodParameter().getParameterName();
                result.getResolvableErrors().stream().map(MessageSourceResolvable::getDefaultMessage)
                        .map(defaultMessage -> new ParamError(parameterName, defaultMessage, ParamErrorType.COOKIE))
                        .forEach(paramErrorList::add);
            }

            @Override
            public void matrixVariable(MatrixVariable matrixVariable, ParameterValidationResult result) {
                log.info("result = {}", result);
            }

            @Override
            public void modelAttribute(@Nullable ModelAttribute modelAttribute, ParameterErrors errors) {
                log.info("errors = {}", errors);
            }

            @Override
            public void pathVariable(PathVariable pathVariable, ParameterValidationResult result) {
                log.info("result = {}", result);
            }

            @Override
            public void requestBody(RequestBody requestBody, ParameterErrors errors) {
                log.info("errors = {}", errors);
                errors.getAllErrors().stream().map(ParamError::new).forEach(paramErrorList::add);
            }

            @Override
            public void requestHeader(RequestHeader requestHeader, ParameterValidationResult result) {
                log.info("result = {}", result);
            }

            @Override
            public void requestParam(@Nullable RequestParam requestParam, ParameterValidationResult result) {
                log.info("result = {}", result);
                String parameterName = result.getMethodParameter().getParameterName();
                result.getResolvableErrors().stream().map(MessageSourceResolvable::getDefaultMessage)
                        .map(defaultMessage -> new ParamError(parameterName, defaultMessage, ParamErrorType.PARAMETER))
                        .forEach(paramErrorList::add);
            }

            @Override
            public void requestPart(RequestPart requestPart, ParameterErrors errors) {
                log.info("errors = {}", errors);
            }

            @Override
            public void other(ParameterValidationResult result) {
                log.info("result = {}", result);
            }

            @Override
            public void requestBodyValidationResult(RequestBody requestBody, ParameterValidationResult result) {
                log.info("result = {}", result);
                HandlerMethodValidationException.Visitor.super.requestBodyValidationResult(requestBody, result);
            }
        });
        NestedProblemDetail nestedProblemDetail = new NestedProblemDetail(ex.getBody());
        nestedProblemDetail.setErrors(paramErrorList);
        return handleExceptionInternal(ex, nestedProblemDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> createResponseEntity(@Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        switch (body) {
            case null -> {
                NestedProblemDetail nestedProblemDetail = new NestedProblemDetail();
                nestedProblemDetail.setErrorCode(ErrorCode.httpStatusCode(statusCode));
                body = nestedProblemDetail;
            }
            case NestedProblemDetail nestedProblemDetail -> {
                if (null == nestedProblemDetail.getErrorCode()) {
                    nestedProblemDetail.setErrorCode(ErrorCode.httpStatusCode(statusCode));
                }
            }
            case ProblemDetail problemDetail -> body = new NestedProblemDetail(problemDetail);
            default -> {
            }
        }

        return super.createResponseEntity(body, headers, statusCode, request);
    }

    @Override
    protected ProblemDetail createProblemDetail(Exception ex, HttpStatusCode status, String defaultDetail, @Nullable String detailMessageCode, Object @Nullable [] detailMessageArguments, WebRequest request) {
        ProblemDetail problemDetail = super.createProblemDetail(ex, status, defaultDetail, detailMessageCode, detailMessageArguments, request);
        return new NestedProblemDetail(problemDetail);
    }
}
