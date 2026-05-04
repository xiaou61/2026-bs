package com.privacyauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.privacyauth.mapper")
public class PrivacyAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrivacyAuthApplication.class, args);
    }
}
