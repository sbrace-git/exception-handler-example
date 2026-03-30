package com.github.sbracely.nested.problem.detail.test.mvc.test.controller.api.version;

import com.github.sbracely.nested.problem.detail.test.mvc.NestedProblemDetailApplicationTest;
import com.github.sbracely.nested.problem.detail.response.NestedProblemDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@AutoConfigureRestTestClient
@SpringBootTest(classes = NestedProblemDetailApplicationTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.mvc.apiversion.use.header=API-Version",
        "spring.mvc.apiversion.supported=1,2",
})
@Import(ApiVersionTests.ApiVersionTestController.class)
class ApiVersionTests {

    @Autowired
    private RestTestClient restTestClient;

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
        EntityExchangeResult<NestedProblemDetail> result = restTestClient.get()
                .uri(uri)
                .header("API-Version", "2")
                .exchange()
                .expectStatus()
                .isEqualTo(BAD_REQUEST)
                .expectHeader()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .expectBody(NestedProblemDetail.class)
                .returnResult();
        NestedProblemDetail nestedProblemDetail = result.getResponseBody();
        log.info("nestedProblemDetail: {}", nestedProblemDetail);
        assertThat(nestedProblemDetail).isNotNull();
        assertThat(nestedProblemDetail.getDetail()).isEqualTo("Invalid API version: '2.0.0'.");
        assertThat(nestedProblemDetail.getInstance()).isEqualTo(URI.create("/api-version-test"));
        assertThat(nestedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(nestedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());

    }
}
