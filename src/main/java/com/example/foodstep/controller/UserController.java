package com.example.foodstep.controller;

import com.example.foodstep.dto.EmailRegisterRequest;
import com.example.foodstep.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRegisterService userRegisterService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody EmailRegisterRequest emailRegisterRequest) {
        userRegisterService.save(emailRegisterRequest);
        return new ResponseEntity<>("Register Success", HttpStatus.OK);
    }

}
