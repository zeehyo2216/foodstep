package com.example.foodstep.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400 BAD_REQUEST : 잘못된 요청
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "자기 자신은 팔로우 할 수 없습니다"),
    CANNOT_SEND_VERIFICATION_MAIL(BAD_REQUEST, "인증 메일 전송에 실패하였습니다"),
    USER_EXISTS(BAD_REQUEST, "이미 가입한 사용자입니다"),
    WRONG_VERIFICATION_CODE(BAD_REQUEST, "인증 코드 잘못 입력하였습니다"),
    PLACE_EXISTS(BAD_REQUEST, "이미 등록된 음식점입니다"),

    // 401 UNAUTHORIZED : 인증되지 않은 사용자
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    ACCESS_TOKEN_EXPIRED(UNAUTHORIZED, "이미 만료된 액세스 토큰입니다"),

    // 404 NOT_FOUND : Resource 를 찾을 수 없음
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),
    VERIFICATION_CODE_EXPIRED(NOT_FOUND, "인증이 만료되었습니다. 처음부터 다시 시도해주세요."),
    PLACE_NOT_FOUND(NOT_FOUND, "등록되지 않은 음식점입니다"),
    REVIEW_NOT_FOUND(NOT_FOUND, "등록되지 않은 발자국입니다")


    // 504

    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
