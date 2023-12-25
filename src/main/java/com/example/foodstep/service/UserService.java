package com.example.foodstep.service;

import com.example.foodstep.component.JwtTokenProvider;
import com.example.foodstep.domain.User;
import com.example.foodstep.dto.JwtTokenDto;
import com.example.foodstep.dto.user.*;
import com.example.foodstep.enums.Authority;
import com.example.foodstep.enums.LoginType;
import com.example.foodstep.model.CustomException;
import com.example.foodstep.repository.UserRepository;
import com.example.foodstep.util.VerificationCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.example.foodstep.enums.ErrorCode.*;
import static com.example.foodstep.enums.LoginType.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RedisTemplate<String, String> redisTemplate;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final MailService mailService;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
    private static final String REFRESH_TOKEN_PREFIX = "Refresh ";
    private static final long VERIFICATION_CODE_EXPIRE_TIME = 1000 * 60 * 5;
    private static final String VERIFICATION_CODE_PREFIX = "VerificationCode ";

    private static final long IS_VERIFIED_EXPIRE_TIME = 1000 * 60 * 60 * 12;
    private static final String IS_VERIFIED_PREFIX = "IsVerified ";
    public static final String LOGOUT_VALUE = "logout";

    @Transactional
    public void sendVerificationCode(EmailVerifyRequestDto emailVerifyRequestDto) {

        String requestEmail = emailVerifyRequestDto.getEmail();

        if ( userRepository.existsByEmail(requestEmail) ) {
            throw new CustomException(USER_EXISTS);
        }

        String title = "[Foodstep] 가입 인증 코드";
        String prefix = "인증코드 : ";
        String verificationCode = VerificationCodeUtil.createVerificationCode();

        //인증메일 보내기
        mailService.sendEmail(requestEmail, title, prefix + verificationCode);

        redisTemplate.opsForValue().set(
                VERIFICATION_CODE_PREFIX + requestEmail,
                verificationCode,
                VERIFICATION_CODE_EXPIRE_TIME,
                TimeUnit.MILLISECONDS
        );

    }

    @Transactional
    public void verifyEmail(VerificationCodeRequestDto verificationCodeRequestDto) {
        String redisCode = redisTemplate
                .opsForValue()
                .get(VERIFICATION_CODE_PREFIX + verificationCodeRequestDto.getEmail());

        if (redisCode == null) {
            throw new CustomException(VERIFICATION_CODE_EXPIRED);
        } else if (!redisCode.equals(verificationCodeRequestDto.getVerificationCode())) {
            throw new CustomException(WRONG_VERIFICATION_CODE);
        } else {
            //인증 유무 Redis Cache
            redisTemplate.opsForValue().set(
                    IS_VERIFIED_PREFIX + verificationCodeRequestDto.getEmail(),
                    "email",
                    IS_VERIFIED_EXPIRE_TIME,
                    TimeUnit.MILLISECONDS
            );
        }

    }

    @Transactional
    public void register(RegisterRequestDto registerRequestDto, Authority authority) {
        String redisCode = redisTemplate
                .opsForValue()
                .get(IS_VERIFIED_PREFIX + registerRequestDto.getVerifyTypeInfo());

        if (redisCode == null) {
            throw new CustomException(VERIFICATION_CODE_EXPIRED);
        } else {
            userRepository.save(registerRequestDto.toUserEntity(authority, bCryptPasswordEncoder, redisCode));
        }
    }

    @Transactional
    public JwtTokenDto login(LoginRequestDto loginRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        LoginType loginType = loginRequestDto.getLoginType();
        String loginValue = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();
        String username;

        if (loginType == EMAIL) {
            username = userRepository.findByEmail(loginValue).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND)).getUsername();
        } else if (loginType == PHONE) {
            username = userRepository.findByPhone(loginValue).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND)).getUsername();
        } else {
            username = loginValue;
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generate(authentication);

        //4. Refresh Token Redis에 저장
        redisTemplate.opsForValue().set(
                REFRESH_TOKEN_PREFIX + authentication.getName(),
                jwtTokenDto.getRefreshToken(),
                REFRESH_TOKEN_EXPIRE_TIME,
                TimeUnit.MILLISECONDS
        );
        return jwtTokenDto;
    }

    @Transactional
    public void logout(JwtTokenDto jwtTokenDto, User user) {
        // Access Token Blacklist
        if (!jwtTokenProvider.validateToken(jwtTokenDto.getAccessToken())) {
            throw new CustomException(ACCESS_TOKEN_EXPIRED);
        }

        if (jwtTokenProvider.getExpiration(jwtTokenDto.getAccessToken()) != 0) {
            redisTemplate.opsForValue().set(
                    jwtTokenDto.getAccessToken(),
                    LOGOUT_VALUE,
                    jwtTokenProvider.getExpiration(jwtTokenDto.getAccessToken()),
                    TimeUnit.MILLISECONDS
            );
        }

        // Refresh Token 삭제
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + user.getEmail());

    }

    @Transactional
    public JwtTokenDto reissue(JwtTokenDto jwtTokenDto) {

        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(jwtTokenDto.getRefreshToken())) {
            throw new CustomException(INVALID_REFRESH_TOKEN);
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(jwtTokenDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        String refreshToken = redisTemplate.opsForValue().get(authentication.getName());

        // 4. Refresh Token 일치하는지 검사
        if (refreshToken != null && !refreshToken.equals(jwtTokenDto.getRefreshToken())) {
            throw new CustomException(MISMATCH_REFRESH_TOKEN);
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
