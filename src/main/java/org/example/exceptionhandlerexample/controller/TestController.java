package org.example.exceptionhandlerexample.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptionhandlerexample.reuqest.user.UserRequest;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/hello")
    public Object hello(@Validated UserRequest userRequest) {
        log.info("userRequest={}", userRequest);
        return userRequest;
    }

    @GetMapping("/echo")
    public String echo(@RequestParam String echo) {
        log.info("echo={}", echo);
        return echo;
    }

    @PostMapping("/list")
    public List<UserRequest> list(@RequestBody @Valid List<UserRequest> userRequestList) {
        log.info("userRequestList={}", userRequestList);
        return userRequestList;
    }

    @GetMapping(value = "/cookie", produces = MediaType.TEXT_PLAIN_VALUE)
    public String cookieValue(@CookieValue
                              @NotNull(message = "不能是 null")
                              @Length(min = 2, message = "长度最小是 2")
                              String cookieValue) {
        log.info("cookieValue={}", cookieValue);
        return cookieValue;
    }
}
