package com.github.sbracely.extended.problem.detail.test.flux.test;

import com.github.sbracely.extended.problem.detail.response.Error;
import com.github.sbracely.extended.problem.detail.response.ExtendedProblemDetail;
import com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController;
import com.github.sbracely.extended.problem.detail.test.flux.reuqest.ProblemDetailRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.method.ParameterErrors;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.NotAcceptableStatusException;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.ALLOW;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

@Slf4j
@SpringBootTest
@AutoConfigureWebTestClient(timeout = "PT10M")
class FluxExtendedProblemDetailTests {

    @Autowired
    private WebTestClient webTestClient;

    private static final String BASE_PATH = "/flux-extended-problem-detail";

    /**
     * {@link MethodNotAllowedException}
     * {@link FluxExtendedProblemDetailController#methodNotAllowedException()}.
     */
    @Test
    void methodNotAllowedException() {
        String uri = BASE_PATH + "/method-not-allowed-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.delete().uri(uri).exchange()
                .expectStatus().isEqualTo(METHOD_NOT_ALLOWED)
                .expectHeader().valueEquals(ALLOW, GET.name())
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(METHOD_NOT_ALLOWED.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(METHOD_NOT_ALLOWED.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Supported methods: [GET]");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).isNull();
    }

    /**
     * {@link NotAcceptableStatusException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#notAcceptableStatusException()}.
     */
    @Test
    void notAcceptableStatusException() {
        String uri = BASE_PATH + "/not-acceptable-status-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .header(ACCEPT, APPLICATION_XML_VALUE)
                .exchange()
                .expectStatus().isEqualTo(NOT_ACCEPTABLE)
                .expectHeader().valueEquals(ACCEPT, APPLICATION_JSON_VALUE)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(NOT_ACCEPTABLE.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(NOT_ACCEPTABLE.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Acceptable representations: [application/json].");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).isNull();
    }

    /**
     * {@link org.springframework.web.server.UnsupportedMediaTypeStatusException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#unsupportedMediaTypeStatusException()}.
     */
    @Test
    void unsupportedMediaTypeStatusException() {
        String uri = BASE_PATH + "/unsupported-media-type-status-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.post().uri(uri).exchange()
                .expectStatus().isEqualTo(UNSUPPORTED_MEDIA_TYPE)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectHeader().valueEquals(ACCEPT, APPLICATION_XML_VALUE)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(UNSUPPORTED_MEDIA_TYPE.value());
        assertThat(extendedProblemDetail.getDetail()).isNull();
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).isNull();
    }

    /**
     * {@link org.springframework.web.server.MissingRequestValueException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#missingRequestValueException(String)} ()}.
     */
    @Test
    void missingRequestValueException() {
        String uri = BASE_PATH + "/missing-request-value-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri).exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Required query parameter 'id' is not present.");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).isNull();
    }

    /**
     * {@link org.springframework.web.server.UnsatisfiedRequestParameterException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#unsatisfiedRequestParameterException()}.
     */
    @Test
    void unsatisfiedRequestParameterException() {
        String uri = BASE_PATH + "/unsatisfied-request-parameter-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isNotNull();
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).isNull();
    }

    /**
     * {@link org.springframework.web.bind.support.WebExchangeBindException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#webExchangeBindException(ProblemDetailRequest)} ()}.
     */
    @Test
    void webExchangeBindException() {
        String uri = BASE_PATH + "/web-exchange-bind-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.post().uri(uri)
                .contentType(APPLICATION_JSON)
                .bodyValue("""
                        {
                            "name": "abc",
                            "password": "123"
                        }
                        """)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Invalid request content.");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getErrors()).containsExactlyInAnyOrder(
                new Error(Error.Type.PARAMETER, "name", "姓名长度范围 6-10"),
                new Error(Error.Type.PARAMETER, "age", "年龄不可为空"),
                new Error(Error.Type.PARAMETER, "password", "密码与确认密码不一致"),
                new Error(Error.Type.PARAMETER, "confirmPassword", "密码与确认密码不一致")
        );
    }

    /**
     * {@link  org.springframework.web.method.annotation.HandlerMethodValidationException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#handlerMethodValidationExceptionCookieValue(String)} ()}.
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException.Visitor#cookieValue(CookieValue, ParameterValidationResult)}
     */
    @Test
    void handlerMethodValidationExceptionCookieValue() {
        String uri = BASE_PATH + "/handler-method-validation-exception-cookie-value";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .cookie("cookieValue", "")
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Validation failure");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).singleElement()
                .isEqualTo(new Error(Error.Type.COOKIE, "cookieValue", "cookie 不能为空"));
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#handlerMethodValidationExceptionMatrix(String, List)} )}.
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException.Visitor#matrixVariable(MatrixVariable, ParameterValidationResult)}
     */
    @Test
    void handlerMethodValidationExceptionMatrixVariable() {
        String uri = BASE_PATH + "/handler-method-validation-exception-matrix/abc;list=a,b,c";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Validation failure");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).singleElement()
                .isEqualTo(new Error(Error.Type.PARAMETER, "list", "list 最大长度是 2"));
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#handlerMethodValidationExceptionModelAttribute(ProblemDetailRequest)} ()}.
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException.Visitor#modelAttribute(ModelAttribute, ParameterErrors)}
     */
    @Test
    void handlerMethodValidationExceptionModelAttribute() {
        String uri = BASE_PATH + "/handler-method-validation-exception-model-attribute";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Validation failure");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).singleElement()
                .isEqualTo(new Error(Error.Type.PARAMETER, "password", "密码不能是空"));
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#handlerMethodValidationExceptionPathVariable(String)} ()}.
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException.Visitor#pathVariable(PathVariable, ParameterValidationResult)}
     */
    @Test
    void handlerMethodValidationExceptionPathVariable() {
        String uri = BASE_PATH + "/handler-method-validation-exception-path-variable/abc";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Validation failure");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).singleElement()
                .isEqualTo(new Error(Error.Type.PARAMETER, "id", "id 长度至少 5"));
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#handlerMethodValidationExceptionRequestBody(ProblemDetailRequest)} ()}.
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException.Visitor#requestBody(RequestBody, ParameterErrors)}
     */
    @Test
    void handlerMethodValidationExceptionRequestBody() {
        String uri = BASE_PATH + "/handler-method-validation-exception-request-body";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.post().uri(uri)
                .contentType(APPLICATION_JSON)
                .bodyValue("""
                        {
                            "name": "abc"
                        }
                        """)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Validation failure");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).singleElement()
                .isEqualTo(new Error(Error.Type.PARAMETER, "password", "密码不能是空"));
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#handlerMethodValidationExceptionRequestBodyValidationResult(List)} ()}.
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException.Visitor#requestBodyValidationResult(RequestBody, ParameterValidationResult)}
     */
    @Test
    void handlerMethodValidationExceptionRequestBodyValidationResult() {
        String uri = BASE_PATH + "/handler-method-validation-exception-request-body-validation-result";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.post().uri(uri)
                .contentType(APPLICATION_JSON)
                .bodyValue("""
                        ["", "a"]
                        """)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Validation failure");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).singleElement()
                .isEqualTo(new Error(Error.Type.PARAMETER, null, "元素不能包含空"));
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#handlerMethodValidationExceptionRequestHeader(String)} ()}.
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException.Visitor#requestHeader(RequestHeader, ParameterValidationResult)}
     */
    @Test
    void handlerMethodValidationExceptionRequestHeader() {
        String uri = BASE_PATH + "/handler-method-validation-exception-request-header";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .header("headerValue", "")
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Validation failure");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).singleElement()
                .isEqualTo(new Error(Error.Type.HEADER, "headerValue", "header 不能为空"));
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#handlerMethodValidationExceptionRequestParam(String, String)} ()}.
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException.Visitor#requestParam(RequestParam, ParameterValidationResult)}
     */
    @Test
    void handlerMethodValidationExceptionRequestParam() {
        String uri = BASE_PATH + "/handler-method-validation-exception-request-param";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(uri)
                        .queryParam("param", "")
                        .queryParam("value", "ab")
                        .build())
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Validation failure");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).containsExactlyInAnyOrder(
                new Error(Error.Type.PARAMETER, "param", "参数不能为空"),
                new Error(Error.Type.PARAMETER, "value", "长度至少 5")
        );
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#handlerMethodValidationExceptionRequestPart(FilePart)} ()}.
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException.Visitor#requestPart(RequestPart, ParameterErrors)}
     */
    @Test
    void handlerMethodValidationExceptionRequestPart() {
        String uri = BASE_PATH + "/handler-method-validation-exception-request-part";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.post().uri(uri)
                .bodyValue(Collections.emptyMap())
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Validation failure");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).singleElement()
                .isEqualTo(new Error(Error.Type.PARAMETER, "file", "文件不能为空"));
    }

    /**
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#handlerMethodValidationExceptionOther(String, String, String)} ()}.
     * {@link org.springframework.web.method.annotation.HandlerMethodValidationException.Visitor#other(ParameterValidationResult)}
     */
    @Test
    @ExtendWith(OutputCaptureExtension.class)
    void handlerMethodValidationExceptionOther(CapturedOutput output) {
        String uri = BASE_PATH + "/handler-method-validation-exception-other";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Validation failure");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).isNull();
        assertThat(output.getOut()).contains(Arrays.asList(
                "codes: [NotBlank.fluxExtendedProblemDetailController#handlerMethodValidationExceptionOther.sessionAttribute, NotBlank.sessionAttribute, NotBlank.java.lang.String, NotBlank], defaultMessage: sessionAttribute 不能为空",
                "codes: [NotBlank.fluxExtendedProblemDetailController#handlerMethodValidationExceptionOther.requestAttribute, NotBlank.requestAttribute, NotBlank.java.lang.String, NotBlank], defaultMessage: requestAttribute 不能为空",
                "codes: [NotBlank.fluxExtendedProblemDetailController#handlerMethodValidationExceptionOther.value, NotBlank.value, NotBlank.java.lang.String, NotBlank], defaultMessage: value 不能为空"
        ));
    }

    /**
     * {@link org.springframework.web.server.ServerWebInputException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#serverWebInputException()}.
     */
    @Test
    void serverWebInputException() {
        String uri = BASE_PATH + "/server-web-input-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("server web input error");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).isNull();
    }

    /**
     * {@link org.springframework.web.server.ServerErrorException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#serverErrorException()}.
     */
    @Test
    void serverErrorException() {
        String uri = BASE_PATH + "/server-error-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(INTERNAL_SERVER_ERROR.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(INTERNAL_SERVER_ERROR.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("server error");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).isNull();
    }

    /**
     * {@link org.springframework.web.server.ResponseStatusException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#responseStatusException()}.
     */
    @Test
    void responseStatusException() {
        String uri = BASE_PATH + "/response-status-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("exception");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).isNull();
    }

    /**
     * {@link org.springframework.web.server.ContentTooLargeException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#contentTooLargeException(byte[])} ()}.
     */
    @Test
    void contentTooLargeException() {
        String uri = BASE_PATH + "/content-too-large-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.post().uri(uri)
                .bodyValue("x".repeat(1024 * 1024)) // 1MB
                .exchange()
                .expectStatus().isEqualTo(CONTENT_TOO_LARGE)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(CONTENT_TOO_LARGE.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(CONTENT_TOO_LARGE.value());
        assertThat(extendedProblemDetail.getDetail()).isNull();
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).isNull();
    }

    /**
     * {@link org.springframework.web.accept.InvalidApiVersionException}
     * {@link org.springframework.web.accept.MissingApiVersionException}
     * {@link org.springframework.web.accept.NotAcceptableApiVersionException}
     */
    @Nested
    @TestPropertySource(properties = {
            "spring.webflux.apiversion.use.header=API-Version",
            "spring.webflux.apiversion.supported=1,2",
    })
    @AutoConfigureWebTestClient(timeout = "PT10M")
    @Import(ApiVersionTests.NotAcceptableApiVersionController.class)
    class ApiVersionTests {

        /**
         * {@link org.springframework.web.accept.InvalidApiVersionException}
         * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#invalidApiVersionException()}
         */
        @Test
        void invalidApiVersionException() {
            String uri = BASE_PATH + "/invalid-api-version-exception";
            ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                    .header("API-Version", "3")
                    .exchange()
                    .expectStatus().isEqualTo(BAD_REQUEST)
                    .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                    .expectBody(ExtendedProblemDetail.class)
                    .returnResult().getResponseBody();
            log.info("extendedProblemDetail: {}", extendedProblemDetail);
            assertThat(extendedProblemDetail).isNotNull();
            assertThat(extendedProblemDetail.getType()).isNull();
            assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
            assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
            assertThat(extendedProblemDetail.getDetail()).isEqualTo("Invalid API version: '3.0.0'.");
            assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
            assertThat(extendedProblemDetail.getProperties()).isNull();
            assertThat(extendedProblemDetail.getErrors()).isNull();
        }

        /**
         * {@link org.springframework.web.accept.MissingApiVersionException}
         * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#missingApiVersionException()}
         */
        @Test
        void missingApiVersionException() {
            String uri = BASE_PATH + "/missing-api-version-exception";
            EntityExchangeResult<ExtendedProblemDetail> result = webTestClient.get()
                    .uri(uri)
                    .exchange()
                    .expectStatus()
                    .isEqualTo(BAD_REQUEST)
                    .expectHeader()
                    .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                    .expectBody(ExtendedProblemDetail.class)
                    .returnResult();
            ExtendedProblemDetail extendedProblemDetail = result.getResponseBody();
            log.info("extendedProblemDetail: {}", extendedProblemDetail);
            assertThat(extendedProblemDetail).isNotNull();
            assertThat(extendedProblemDetail.getDetail()).isEqualTo("API version is required.");
            assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
            assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
            assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        }

        /**
         * {@link org.springframework.web.accept.NotAcceptableApiVersionException}
         */
        @RestController
        static class NotAcceptableApiVersionController {
            @GetMapping(value = "/not-acceptable-api-version", version = "1")
            public Mono<Void> notAcceptableApiVersion() {
                log.info("response status exception not acceptable api version");
                return Mono.empty();
            }
        }

        /**
         * {@link org.springframework.web.accept.NotAcceptableApiVersionException}
         * {@link NotAcceptableApiVersionController#notAcceptableApiVersion()}
         */
        @Test
        void notAcceptableApiVersionException() {
            String uri = "/not-acceptable-api-version";
            ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                    .header("API-Version", "2")
                    .exchange()
                    .expectStatus().isEqualTo(BAD_REQUEST)
                    .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                    .expectBody(ExtendedProblemDetail.class)
                    .returnResult().getResponseBody();
            log.info("extendedProblemDetail: {}", extendedProblemDetail);
            assertThat(extendedProblemDetail).isNotNull();
            assertThat(extendedProblemDetail.getType()).isNull();
            assertThat(extendedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
            assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
            assertThat(extendedProblemDetail.getDetail()).isEqualTo("Invalid API version: '2.0.0'.");
            assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
            assertThat(extendedProblemDetail.getProperties()).isNull();
            assertThat(extendedProblemDetail.getErrors()).isNull();
        }
    }

    /**
     * {@link org.springframework.web.reactive.resource.NoResourceFoundException}
     */
    @Test
    void noResourceFoundException() {
        String uri = BASE_PATH + "/no-resource-found";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isEqualTo(NOT_FOUND)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(NOT_FOUND.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(NOT_FOUND.value());
        assertThat(extendedProblemDetail.getDetail()).contains("No static resource");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).isNull();
    }

    /**
     * {@link org.springframework.web.server.PayloadTooLargeException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#payloadTooLargeException(byte[])} ()}.
     */
    @Test
    void payloadTooLargeException() {
        String uri = BASE_PATH + "/payload-too-large-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.post().uri(uri)
                .bodyValue("text")
                .exchange()
                .expectStatus().isEqualTo(PAYLOAD_TOO_LARGE)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(CONTENT_TOO_LARGE.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(PAYLOAD_TOO_LARGE.value());
        assertThat(extendedProblemDetail.getDetail()).isNull();
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).isNull();
    }

    /**
     * {@link org.springframework.web.ErrorResponseException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#errorResponseException()}.
     */
    @Test
    void errorResponseException() {
        String uri = BASE_PATH + "/error-response-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo("错误标题");
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("错误详情");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).containsExactlyInAnyOrder(
                new Error("错误信息 1"), new Error("错误信息 2")
        );
    }

    /**
     * {@link com.github.sbracely.extended.problem.detail.test.flux.exception.ExtendedErrorResponseException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#extendedErrorResponseException()}.
     */
    @Test
    void extendedErrorResponseException() {
        String uri = BASE_PATH + "/extended-error-response-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo("支付失败标题");
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("支付失败详情");
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(INTERNAL_SERVER_ERROR.value());
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).containsExactlyInAnyOrder(
                new Error("余额不足"),
                new Error("支付频繁")
        );
    }

    /**
     * {@link org.springframework.validation.method.MethodValidationException}
     * {@link com.github.sbracely.extended.problem.detail.test.flux.controller.FluxExtendedProblemDetailController#methodValidationException()}.
     */
    @Test
    void methodValidationException() {
        String uri = BASE_PATH + "/method-validation-exception";
        ExtendedProblemDetail extendedProblemDetail = webTestClient.get().uri(uri)
                .exchange()
                .expectStatus().isEqualTo(INTERNAL_SERVER_ERROR)
                .expectHeader().contentType(APPLICATION_PROBLEM_JSON)
                .expectBody(ExtendedProblemDetail.class)
                .returnResult().getResponseBody();
        log.info("extendedProblemDetail: {}", extendedProblemDetail);
        assertThat(extendedProblemDetail).isNotNull();
        assertThat(extendedProblemDetail.getType()).isNull();
        assertThat(extendedProblemDetail.getTitle()).isEqualTo(INTERNAL_SERVER_ERROR.getReasonPhrase());
        assertThat(extendedProblemDetail.getStatus()).isEqualTo(INTERNAL_SERVER_ERROR.value());
        assertThat(extendedProblemDetail.getDetail()).isEqualTo("Validation failed");
        assertThat(extendedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(extendedProblemDetail.getProperties()).isNull();
        assertThat(extendedProblemDetail.getErrors()).isNull();
    }
}
