package org.example.exceptionhandlerexample.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptionhandlerexample.model.User;
import org.example.exceptionhandlerexample.reuqest.user.UserRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public User get(@PathVariable Integer id) {
        log.info("get id = {}", id);
        User user = new User();
        user.setId(id);
        return user;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        log.info("delete id = {}", id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserRequest put(UserRequest userRequest) {
        log.info("Put userRequest = {}", userRequest);
        return userRequest;
    }

    @PutMapping(path = "/put1", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRequest put1(UserRequest userRequest) {
        log.info("Put userRequest = {}", userRequest);
        return userRequest;
    }

}
