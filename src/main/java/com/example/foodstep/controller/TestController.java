package com.example.foodstep.controller;

import com.example.foodstep.config.RequestUser;
import com.example.foodstep.domain.User;
import com.example.foodstep.model.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String test(){
        return "TEST";
    }

    @GetMapping("/user-info")
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal CustomUser customUser) {
        return ResponseEntity.ok(customUser.getUser());
    }

    @GetMapping("/user-test")
    public ResponseEntity<User> test(@RequestUser User user) {
        return ResponseEntity.ok(user);
    }
}
