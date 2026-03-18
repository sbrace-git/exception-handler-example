package org.example.exceptionhandlerexample.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptionhandlerexample.model.User;
import org.example.exceptionhandlerexample.reuqest.user.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public User get(@PathVariable Integer id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @GetMapping("/query")
    public ResponseEntity<Void> put(@NotBlank String id) {
        log.info("id = {}", id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/upload", params = {"a=1", "a=2", "b=3", "c!=4"})
    public UserRequest upload(UserRequest userRequest) {
//        log.info("Uploading file {}", file.getOriginalFilename());
        log.info("userRequest {}", userRequest);
        return null;
    }
}
