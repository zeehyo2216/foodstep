package com.example.foodstep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableAsync
@SpringBootApplication
public class FoodstepApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodstepApplication.class, args);
    }

}
