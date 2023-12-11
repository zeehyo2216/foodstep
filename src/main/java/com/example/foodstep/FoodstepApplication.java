package com.example.foodstep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@SpringBootApplication
public class FoodstepApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodstepApplication.class, args);
    }

}
