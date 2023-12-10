package com.example.foodstep.service;

import com.example.foodstep.domain.User;
import com.example.foodstep.dto.EmailRegisterRequest;
import com.example.foodstep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Integer save(EmailRegisterRequest emailRegisterRequest) {
        return userRepository.save(User.builder()
                .email(emailRegisterRequest.getEmail())
                .password(bCryptPasswordEncoder.encode(emailRegisterRequest.getPassword()))
                .nickname(emailRegisterRequest.getNickname())
                .build()).getId();
    }
}
