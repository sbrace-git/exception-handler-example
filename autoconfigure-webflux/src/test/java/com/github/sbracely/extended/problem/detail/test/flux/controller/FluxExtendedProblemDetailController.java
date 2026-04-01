package com.github.sbracely.extended.problem.detail.test.flux.controller;

import com.github.sbracely.extended.problem.detail.response.Error;
import com.github.sbracely.extended.problem.detail.response.ExtendedProblemDetail;
import com.github.sbracely.extended.problem.detail.test.flux.exception.ExtendedErrorResponseException;
import com.github.sbracely.extended.problem.detail.test.flux.reuqest.ProblemDetailRequest;
import com.github.sbracely.extended.problem.detail.test.flux.reuqest.valid.annocation.CheckFilePart;
import com.github.sbracely.extended.problem.detail.test.flux.reuqest.valid.annocation.CheckPassword;
import com.github.sbracely.extended.problem.detail.test.flux.service.ProblemDetailService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.PayloadTooLargeException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/flux-extended-problem-detail")
public class FluxExtendedProblemDetailController {

    private final ProblemDetailService problemDetailService;

    public FluxExtendedProblemDetailController(ProblemDetailService problemDetailService) {
        this.problemDetailService = problemDetailService;
    }

    /**
     * {@link org.springframework.web.server.MethodNotAllowedException}
     */
    @GetMapping("/method-not-allowed-exception")
    public Mono<Void> methodNotAllowedException() {
        log.info("method-not-allowed");
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.server.NotAcceptableStatusException}
     */
    @GetMapping(path = "/not-acceptable-status-exception", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> notAcceptableStatusException() {
        log.info("not-acceptable-status");
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.server.UnsupportedMediaTypeStatusException}
     */
    @PostMapping(path = "/unsupported-media-type-status-exception", consumes = MediaType.APPLICATION_XML_VALUE)
    public Mono<Void> unsupportedMediaTypeStatusException() {
        log.info("unsupported media type");
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.bind.MissingRequestValueException}
     */
    @GetMapping("/missing-request-value-exception")
    public Mono<Void> missingRequestValueException(@RequestParam String id) {
        log.info("missing-request-value id: {}", id);
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.server.UnsatisfiedRequestParameterException}
     */
    @GetMapping(path = "/unsatisfied-request-parameter-exception", params = {"type=1", "exist", "!debug"})
    public Mono<Void> unsatisfiedRequestParameterException() {
        log.info("unsatisfied request param");
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.bind.support.WebExchangeBindException}
     */
    @PostMapping("/web-exchange-bind-exception")
    public Mono<Void> webExchangeBindException(@RequestBody @Validated ProblemDetailRequest problemDetailRequest) {
        log.info("web exchange bind: {}", problemDetailRequest);
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     */
    @GetMapping("/handler-method-validation-exception-cookie-value")
    public Mono<Void> handlerMethodValidationExceptionCookieValue(@CookieValue @NotBlank(message = "cookie 不能为空") String cookieValue) {
        log.info("cookieValue: {}", cookieValue);
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     */
    @GetMapping("/handler-method-validation-exception-matrix/{id}")
    public Mono<Void> handlerMethodValidationExceptionMatrix(@PathVariable String id,
                                                             @MatrixVariable @Size(max = 2, message = "list 最大长度是 2") List<String> list) {
        log.info("id: {}, list: {}", id, list);
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     */
    @GetMapping("/handler-method-validation-exception-model-attribute")
    public Mono<Void> handlerMethodValidationExceptionModelAttribute(@CheckPassword(message = "密码不能是空") ProblemDetailRequest problemDetailRequest) {
        log.info("problemDetailRequest: {}", problemDetailRequest);
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     */
    @GetMapping("/handler-method-validation-exception-path-variable/{id}")
    public Mono<Void> handlerMethodValidationExceptionPathVariable(@PathVariable @Size(min = 5, message = "id 长度至少 5") String id) {
        log.info("id: {}", id);
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     */
    @PostMapping("/handler-method-validation-exception-request-body")
    public Mono<Void> handlerMethodValidationExceptionRequestBody(@RequestBody @CheckPassword(message = "密码不能是空") ProblemDetailRequest problemDetailRequest) {
        log.info("problemDetailRequest: {}", problemDetailRequest);
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     */
    @PostMapping("/handler-method-validation-exception-request-body-validation-result")
    public Mono<Void> handlerMethodValidationExceptionRequestBodyValidationResult(@RequestBody List<@NotBlank(message = "元素不能包含空") String> list) {
        log.info("list: {}", list);
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     */
    @GetMapping(path = "/handler-method-validation-exception-request-header")
    public Mono<Void> handlerMethodValidationExceptionRequestHeader(@RequestHeader @NotBlank(message = "header 不能为空") String headerValue) {
        log.info("headerValue: {}", headerValue);
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     */
    @GetMapping("/handler-method-validation-exception-request-param")
    public Mono<Void> handlerMethodValidationExceptionRequestParam(@RequestParam @NotBlank(message = "参数不能为空") String param,
                                                                   @RequestParam @Size(min = 5, message = "长度至少 5") String value) {
        log.info("param: {}, value: {}", param, value);
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     */
    @PostMapping("/handler-method-validation-exception-request-part")
    public Mono<Void> handlerMethodValidationExceptionRequestPart(@RequestPart(required = false)
                                                                  @CheckFilePart(requiredMessage = "文件不能为空") FilePart filePart) {
        log.info("part: {}", filePart);
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     */
    @GetMapping("/handler-method-validation-exception-other")
    public Mono<Void> handlerMethodValidationExceptionOther(
            @SessionAttribute(required = false) @NotBlank(message = "sessionAttribute 不能为空") String sessionAttribute,
            @RequestAttribute(required = false) @NotBlank(message = "requestAttribute 不能为空") String requestAttribute,
            @Value("") @NotBlank(message = "value 不能为空") String value) {
        log.info("sessionAttribute: {}, requestAttribute: {}, value: {}", sessionAttribute, requestAttribute, value);
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.server.ServerWebInputException}
     */
    @GetMapping("/server-web-input-exception")
    public Mono<Void> serverWebInputException() {
        log.info("server web input");
        throw new ServerWebInputException("server web input error");
    }

    /**
     * {@link org.springframework.web.server.ServerErrorException}
     */
    @GetMapping("/server-error-exception")
    public Mono<Void> serverErrorException() {
        log.info("server error");
        throw new ServerErrorException("server error", new RuntimeException());
    }

    /**
     * {@link org.springframework.web.server.ResponseStatusException}
     */
    @GetMapping("/response-status-exception")
    public Mono<Void> responseStatusException() {
        log.info("response status exception");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "exception");
    }

    /**
     * {@link org.springframework.web.server.ContentTooLargeException}
     */
    @PostMapping("/content-too-large-exception")
    public Mono<Void> contentTooLargeException(@RequestBody byte[] body) {
        log.info("body.length: {}", body.length);
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.accept.InvalidApiVersionException}
     */
    @GetMapping("/invalid-api-version-exception")
    public Mono<Void> invalidApiVersionException() {
        log.info("not acceptable api version");
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.accept.MissingApiVersionException}
     */
    @GetMapping("/missing-api-version-exception")
    public Mono<Void> missingApiVersionException() {
        log.info("response status exception missing api version");
        return Mono.empty();
    }

    /**
     * {@link org.springframework.web.server.PayloadTooLargeException}
     */
    @PostMapping("/payload-too-large-exception")
    public Mono<Void> payloadTooLargeException(@RequestBody byte[] body) {
        log.info("body.length: {}", body.length);
        throw new PayloadTooLargeException(new RuntimeException("payload too large"));
    }

    /**
     * {@link org.springframework.web.ErrorResponseException}
     */
    @GetMapping("/error-response-exception")
    public Mono<Void> errorResponseException() {
        log.info("error response");
        ExtendedProblemDetail extendedProblemDetail = new ExtendedProblemDetail();
        extendedProblemDetail.setDetail("错误详情");
        extendedProblemDetail.setTitle("错误标题");
        extendedProblemDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        extendedProblemDetail.setErrors(Lists.newArrayList(new Error("错误信息 1"), new Error("错误信息 2")));
        throw new ErrorResponseException(HttpStatus.BAD_REQUEST, extendedProblemDetail, new RuntimeException("business exception"));
    }

    /**
     * {@link ExtendedErrorResponseException}
     */
    @GetMapping("/extended-error-response-exception")
    public Mono<Void> extendedErrorResponseException() {
        log.info("business");
        ExtendedProblemDetail extendedProblemDetail = new ExtendedProblemDetail();
        extendedProblemDetail.setTitle("支付失败标题");
        extendedProblemDetail.setDetail("支付失败详情");
        extendedProblemDetail.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        extendedProblemDetail.setErrors(Lists.newArrayList(new Error("余额不足"), new Error("支付频繁")));
        throw new ExtendedErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, extendedProblemDetail);
    }

    /**
     * {@link org.springframework.validation.method.MethodValidationException}
     */
    @GetMapping("/method-validation-exception")
    public Mono<Void> methodValidationException() {
        log.info("method validation");
        String result = problemDetailService.createProblemDetail("");
        log.info("result: {}", result);
        return Mono.empty();
    }
}
