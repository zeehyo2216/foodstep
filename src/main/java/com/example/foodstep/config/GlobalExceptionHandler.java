package com.example.foodstep.config;

import com.example.foodstep.dto.ErrorResponseDto;
import com.example.foodstep.model.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        return ErrorResponseDto.toResponseEntity(e.getErrorCode());
    }
}
