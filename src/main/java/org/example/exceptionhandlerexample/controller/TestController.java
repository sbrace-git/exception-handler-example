package org.example.exceptionhandlerexample.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptionhandlerexample.reuqest.user.UserRequest;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class TestController {

    // 路径模板为 "/hello"，没有定义任何路径变量
    @GetMapping("/hello")
    public Object hello(@Valid UserRequest userRequest) {
        log.info("userRequest={}", userRequest);
        return userRequest;
    }
}
