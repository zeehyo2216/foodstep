package com.example.foodstep.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class VerificationCodeUtil {
    private VerificationCodeUtil() {}

    public static String createVerificationCode(){
        int len = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < len; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[NoSuchAlgorithm Exception] 인증코드 생성 실패");
        }
    }

}
