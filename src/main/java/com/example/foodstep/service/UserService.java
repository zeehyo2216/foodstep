package com.example.foodstep.service;

import com.example.foodstep.component.JwtTokenProvider;
import com.example.foodstep.domain.User;
import com.example.foodstep.dto.JwtTokenDto;
import com.example.foodstep.dto.user.*;
import com.example.foodstep.enums.Authority;
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
    private static final long VERIFICATION_CODE_EXPIRE_TIME = 1000 * 60 * 5;
    private static final String VERIFICATION_CODE_PREFIX = "VerificationCode ";

    private static final long IS_VERIFIED_EXPIRE_TIME = 1000 * 60 * 60 * 12;
    private static final String IS_VERIFIED_PREFIX = "IsVerified ";

    @Transactional
    public void sendVerificationCode(EmailVerifyRequestDto emailVerifyRequestDto) {

        String requestEmail = emailVerifyRequestDto.getEmail();

        if ( userRepository.existsByEmail(requestEmail) ) {
            throw new RuntimeException("이미 가입한 사용자입니다.");
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
            throw new RuntimeException("인증이 만료되었습니다. 다시 시도해주세요.");
        } else if (!redisCode.equals(verificationCodeRequestDto.getVerificationCode())) {
            throw new RuntimeException("인증 코드 잘못 입력하였습니다.");
        } else {
            //인증 유무 Redis Cache
            redisTemplate.opsForValue().set(
                    IS_VERIFIED_PREFIX + verificationCodeRequestDto.getEmail(),
                    "1",
                    IS_VERIFIED_EXPIRE_TIME,
                    TimeUnit.MILLISECONDS
            );
        }

    }

    @Transactional
    public void register(EmailRegisterRequestDto emailRegisterRequestDto, Authority authority) {
        String redisCode = redisTemplate
                .opsForValue()
                .get(IS_VERIFIED_PREFIX + emailRegisterRequestDto.getEmail());

        if (redisCode == null) {
            throw new RuntimeException("인증이 만료되었습니다. 처음부터 다시 시도해주세요.");
        } else {
            userRepository.save(User.builder()
                    .email(emailRegisterRequestDto.getEmail())
                    .password(bCryptPasswordEncoder.encode(emailRegisterRequestDto.getPassword()))
                    .nickname(emailRegisterRequestDto.getNickname())
                    .authority(authority)
                    .build());
        }
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
    public void logout(User user) {


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
