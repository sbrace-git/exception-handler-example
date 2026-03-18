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

    @DeleteMapping("/v2/{id}")
    public void deleteV2(@PathVariable Integer iid) {
        log.info("delete iid = {}", iid);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserRequest put(UserRequest userRequest) {
        log.info("put userRequest = {}", userRequest);
        return userRequest;
    }

    @PutMapping(path = "/v2", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRequest putV2(UserRequest userRequest) {
        log.info("put userRequest = {}", userRequest);
        return userRequest;
    }

}
