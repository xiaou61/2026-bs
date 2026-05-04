package com.phishingtraining;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.phishingtraining.mapper")
public class PhishingTrainingApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhishingTrainingApplication.class, args);
    }
}
