package com.example.foodstep.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 카카오 API 에서 제공하는 category_name 과 일치하도록 설정.
@Getter
@AllArgsConstructor
public enum Category {
    KR_FOOD("한식");
    private final String name;
}
