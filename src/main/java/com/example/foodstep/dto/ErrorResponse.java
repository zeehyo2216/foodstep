package com.example.foodstep.dto;

import com.example.foodstep.enums.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final OffsetDateTime timestamp = OffsetDateTime.now();
    private final int status;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .code(errorCode.name())
                        .message(errorCode.getDetail())
                        .build()
                );
    }
}