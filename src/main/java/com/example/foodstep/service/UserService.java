package com.example.foodstep.service;

import com.example.foodstep.component.JwtTokenProvider;
import com.example.foodstep.domain.User;
import com.example.foodstep.dto.EmailRegisterRequestDto;
import com.example.foodstep.dto.EmailUserRequestDto;
import com.example.foodstep.dto.JwtTokenDto;
import com.example.foodstep.enums.Authority;
import com.example.foodstep.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RedisTemplate<String, String> redisTemplate;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    public void register(EmailRegisterRequestDto emailRegisterRequestDto, Authority authority) {
        if ( userRepository.existsByEmail(emailRegisterRequestDto.getEmail()) ) {
            throw new RuntimeException("이미 가입한 사용자입니다.");
        }
        userRepository.save(User.builder()
                .email(emailRegisterRequestDto.getEmail())
                .password(bCryptPasswordEncoder.encode(emailRegisterRequestDto.getPassword()))
                .nickname(emailRegisterRequestDto.getNickname())
                .authority(authority)
                .build());
    }

    @Transactional
    public JwtTokenDto login(EmailUserRequestDto emailUserRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = emailUserRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generate(authentication);

        //4. Refresh Token Redis에 저장
        redisTemplate.opsForValue().set(
                authentication.getName(),
                jwtTokenDto.getRefreshToken(),
                REFRESH_TOKEN_EXPIRE_TIME,
                TimeUnit.MILLISECONDS
        );
        return jwtTokenDto;
    }

    @Transactional
    public JwtTokenDto reissue(JwtTokenDto jwtTokenDto) {

        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(jwtTokenDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        String refreshToken = redisTemplate.opsForValue().get(authentication.getName());

        // 4. Refresh Token 일치하는지 검사
        if (refreshToken != null && !refreshToken.equals(jwtTokenDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        JwtTokenDto newTokenDto = jwtTokenProvider.generate(authentication);

        // 6. Refresh Token 업데이트
        redisTemplate.opsForValue().set(
                authentication.getName(),
                newTokenDto.getRefreshToken(),
                REFRESH_TOKEN_EXPIRE_TIME,
                TimeUnit.MILLISECONDS
        );

        // 토큰 발급
        return newTokenDto;
    }


}
