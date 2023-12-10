package com.example.foodstep.controller;

import com.example.foodstep.dto.EmailRegisterRequestDto;
import com.example.foodstep.enums.Authority;
import com.example.foodstep.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody EmailRegisterRequestDto emailRegisterRequestDto) {
        userService.register(emailRegisterRequestDto, Authority.ROLE_ADMIN);
        return new ResponseEntity<>("Register Success", HttpStatus.OK);
    }
}
