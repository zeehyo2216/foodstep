package com.example.foodstep.util;


import java.time.Duration;
import java.time.OffsetDateTime;

public class DateUtil {
    private static final String PASSED_SUFFIX = " 전";

    public static String getFeedDate(OffsetDateTime dateInit) {
        OffsetDateTime current = OffsetDateTime.now();

        Duration duration = Duration.between(dateInit.toLocalDateTime(), current.toLocalDateTime());

        long seconds = duration.getSeconds();

        if (seconds <= 60) {
            return "1분" + PASSED_SUFFIX;
        } else if (seconds <= 3600) {
            long minutes = duration.toMinutes();
            return String.valueOf(minutes) + "분" + PASSED_SUFFIX;
        } else if (seconds <= 86400) {
            long hours = duration.toHours();
            return String.valueOf(hours) + "시간" + PASSED_SUFFIX;
        } else if (seconds <= 604800) {
            long days = duration.toDays();
            return String.valueOf(days) + "일" + PASSED_SUFFIX;
        } else if (seconds <= 6048000) {
            long weeks = duration.toDays() / 7;
            return String.valueOf(weeks) + "주" + PASSED_SUFFIX;
        } else {
            long months = duration.toDays() / 30;
            return String.valueOf(months) + "달" + PASSED_SUFFIX;
        }

        //

    }
}
