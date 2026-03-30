package com.github.sbracely.nested.problem.detail.test.mvc.test.controller.endpoint;

import com.github.sbracely.nested.problem.detail.test.mvc.NestedProblemDetailApplicationTest;
import lombok.extern.slf4j.Slf4j;
import com.github.sbracely.nested.problem.detail.response.NestedProblemDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON;

@Slf4j
@SpringBootTest(classes = NestedProblemDetailApplicationTest.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = "management.endpoints.web.exposure.include=demo")
public class EndpointTests {
    @Autowired
    private MockMvcTester mockMvcTester;

    private static final String BASE_PATH = "/actuator";

    @Test
    void errorResponseExceptionAbstractWebMvcEndpointHandlerMappingInvalidEndpointBadRequestException() {
        String uri = BASE_PATH + "/demo/name";
        MvcTestResult result = mockMvcTester.get().uri(uri).exchange();
        assertThat(result)
                .hasStatus(BAD_REQUEST)
                .hasContentType(APPLICATION_PROBLEM_JSON);
        NestedProblemDetail nestedProblemDetail = assertThat(result).bodyJson()
                .convertTo(NestedProblemDetail.class).isNotNull().actual();
        log.info("nestedProblemDetail: {}", nestedProblemDetail);
        assertThat(nestedProblemDetail.getDetail()).containsOnlyOnce("Missing parameters: ")
                .contains("param1", "param2");
        assertThat(nestedProblemDetail.getInstance()).isEqualTo(URI.create(uri));
        assertThat(nestedProblemDetail.getStatus()).isEqualTo(BAD_REQUEST.value());
        assertThat(nestedProblemDetail.getTitle()).isEqualTo(BAD_REQUEST.getReasonPhrase());
        assertThat(nestedProblemDetail.getErrors()).isNull();
    }
}
