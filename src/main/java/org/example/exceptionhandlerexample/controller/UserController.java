package org.example.exceptionhandlerexample.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptionhandlerexample.model.User;
import org.example.exceptionhandlerexample.reuqest.user.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/get/{id}/aa")
    public User get(@PathVariable Integer id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @GetMapping("/query")
    public ResponseEntity<Void> put(@RequestParam String id, @RequestParam String name, @RequestHeader String header) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/upload", params = {"a=1", "a=2", "b=3", "c!=4"})
    public UserRequest upload(@Valid UserRequest userRequest) {
//        log.info("Uploading file {}", file.getOriginalFilename());
        log.info("userRequest {}", userRequest);
        return null;
    }
}
