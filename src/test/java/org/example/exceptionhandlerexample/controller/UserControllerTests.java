package org.example.exceptionhandlerexample.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void httpRequestMethodNotSupportedExceptionTest() throws Exception {
        String url = "/user/1";
        mockMvc.perform(MockMvcRequestBuilders.post(url))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(header().string("Allow", allOf(
                        containsString(HttpMethod.GET.name()),
                        containsString(HttpMethod.DELETE.name())
                )))
                .andExpect(jsonPath("$.detail").value(allOf(
                        containsString(HttpMethod.POST.name()),
                        containsString("not supported")
                )))
                .andExpect(jsonPath("$.errorCode").value("A00405"))
                .andExpect(jsonPath("$.instance").value(url))
                .andExpect(jsonPath("$.status").value(HttpStatus.METHOD_NOT_ALLOWED.value()))
                .andExpect(jsonPath("$.title").value(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase()));
    }

    @Test
    void httpMediaTypeNotSupportedExceptionTest() throws Exception {
        String url = "/user";
        mockMvc.perform(MockMvcRequestBuilders.put(url))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(header().string("Accept", Matchers.is(MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(jsonPath("$.detail").value(allOf(
                        containsString("null"),
                        containsString("not supported")
                )))
                .andExpect(jsonPath("$.errorCode").value("A00415"))
                .andExpect(jsonPath("$.instance").value(url))
                .andExpect(jsonPath("$.status").value(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                .andExpect(jsonPath("$.title").value(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase()));
    }

    @Test
    void httpMediaTypeNotAcceptableExceptionTest() throws Exception {
        String url = "/user/v2";
        mockMvc.perform(MockMvcRequestBuilders.put(url).header("Accept", MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(header().string("Accept", Matchers.is(MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(jsonPath("$.detail").value(allOf(
                        containsString(MediaType.APPLICATION_JSON_VALUE),
                        containsString("Acceptable")
                )))
                .andExpect(jsonPath("$.errorCode").value("A00406"))
                .andExpect(jsonPath("$.instance").value(url))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_ACCEPTABLE.value()))
                .andExpect(jsonPath("$.title").value(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase()));
    }

    @Test
    void MissingPathVariableExceptionTest() throws Exception {
        String url = "/user/v2/1";
        mockMvc.perform(MockMvcRequestBuilders.delete(url))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value(Matchers.containsString("path variable")))
                .andExpect(jsonPath("$.errorCode").value("A00500"))
                .andExpect(jsonPath("$.instance").value(url))
                .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("$.title").value(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

}
