package com.xiaou.seniorhealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SeniorHealthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeniorHealthApplication.class, args);
    }
}
