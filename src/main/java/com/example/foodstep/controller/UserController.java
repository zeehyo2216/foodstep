package com.example.foodstep.controller;

import com.example.foodstep.config.RequestUser;
import com.example.foodstep.domain.User;
import com.example.foodstep.dto.JwtTokenDto;
import com.example.foodstep.dto.user.EmailVerifyRequestDto;
import com.example.foodstep.dto.user.LoginRequestDto;
import com.example.foodstep.dto.user.RegisterRequestDto;
import com.example.foodstep.dto.user.VerificationCodeRequestDto;
import com.example.foodstep.enums.Authority;
import com.example.foodstep.service.UserService;
import com.example.foodstep.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/send-verify-email")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody @Valid EmailVerifyRequestDto emailVerifyRequestDto) {
        userService.sendVerificationCode(emailVerifyRequestDto);
        return new ResponseEntity<>("Success sending Verification email.", HttpStatus.OK);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> VerifyEmail(@RequestBody VerificationCodeRequestDto verificationCodeRequestDto) {
        userService.verifyEmail(verificationCodeRequestDto);
        return new ResponseEntity<>("Email Verified.", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        userService.register(registerRequestDto, Authority.ROLE_USER);
        return new ResponseEntity<>("Register Success.", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(userService.login(loginRequestDto), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, @RequestUser User user) {
        String accessToken = JwtUtil.resolveToken(request);
        userService.logout(accessToken, user.getUsername());
        return new ResponseEntity<>("Logout Success.", HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity<JwtTokenDto> reissue(HttpServletRequest request, @RequestBody JwtTokenDto jwtTokenDto) { //only refreshToken
        String accessToken = JwtUtil.resolveToken(request);
        return new ResponseEntity<>(userService.reissue(accessToken, jwtTokenDto), HttpStatus.OK);
    }

}
