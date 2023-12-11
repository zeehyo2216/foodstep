package com.example.foodstep.config;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Documented
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : user")
public @interface RequestUser {
}
