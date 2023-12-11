package com.example.foodstep.controller;

import com.example.foodstep.config.RequestUser;
import com.example.foodstep.domain.User;
import com.example.foodstep.dto.JwtTokenDto;
import com.example.foodstep.dto.user.EmailRegisterRequestDto;
import com.example.foodstep.dto.user.EmailUserRequestDto;
import com.example.foodstep.dto.user.EmailVerifyRequestDto;
import com.example.foodstep.dto.user.VerificationCodeRequestDto;
import com.example.foodstep.enums.Authority;
import com.example.foodstep.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/send-verify-email")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody EmailVerifyRequestDto emailVerifyRequestDto) {
        userService.sendVerificationCode(emailVerifyRequestDto);
        return new ResponseEntity<>("Success sending Verification email.", HttpStatus.OK);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> VerifyEmail(@RequestBody VerificationCodeRequestDto verificationCodeRequestDto) {
        userService.verifyEmail(verificationCodeRequestDto);
        return new ResponseEntity<>("Email Verified.", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody EmailRegisterRequestDto emailRegisterRequestDto) {
        userService.register(emailRegisterRequestDto, Authority.ROLE_USER);
        return new ResponseEntity<>("Register Success.", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody EmailUserRequestDto emailUserRequestDto) {
        return new ResponseEntity<>(userService.login(emailUserRequestDto), HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestUser User user) {
        userService.logout(user);
        return new ResponseEntity<>("Logout Success.", HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity<JwtTokenDto> reissue(@RequestBody JwtTokenDto jwtTokenDto) {
        return new ResponseEntity<>(userService.reissue(jwtTokenDto), HttpStatus.OK);
    }

}
