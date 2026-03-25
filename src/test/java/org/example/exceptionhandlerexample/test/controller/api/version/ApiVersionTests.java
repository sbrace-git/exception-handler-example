package org.example.exceptionhandlerexample.test.controller.api.version;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptionhandlerexample.response.NestedProblemDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.mvc.apiversion.use.header=API-Version",
        "spring.mvc.apiversion.supported=1,2",
})
@Import(ApiVersionTests.ApiVersionTestController.class)
class ApiVersionTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @RestController
    static class ApiVersionTestController {
        @GetMapping(path = "/api-version-test", version = "1")
        void apiVersionTest1() {
        }
    }

    @Test
    void errorResponseExceptionNotAcceptableApiVersionException() {
        String uri = "http://localhost:" + port + "/api-version-test";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("API-Version", List.of("2"));
        HttpEntity<Void> objectHttpEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<NestedProblemDetail> exchange = restTemplate.exchange(uri, HttpMethod.GET, objectHttpEntity, NestedProblemDetail.class);
        assertThat(exchange.getStatusCode()).isEqualTo(BAD_REQUEST);
        assertThat(exchange.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_PROBLEM_JSON);
        NestedProblemDetail nestedProblemDetail = exchange.getBody();
        log.info("nestedProblemDetail: {}", nestedProblemDetail);
        assertThat(nestedProblemDetail).isNotNull();
        assertThat(nestedProblemDetail.getDetail()).isEqualTo("Invalid API version: '2.0.0'.");
        assertThat(nestedProblemDetail.getErrorCode()).isEqualTo("A00400");
        assertThat(nestedProblemDetail.getInstance()).isEqualTo(URI.create("/api-version-test"));
        assertThat(nestedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(nestedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());

    }
}
