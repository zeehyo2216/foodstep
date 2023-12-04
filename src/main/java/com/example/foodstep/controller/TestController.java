package com.example.foodstep.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String test(){
        return "TEST";
    }
}
