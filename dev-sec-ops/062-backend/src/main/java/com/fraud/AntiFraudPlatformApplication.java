package com.fraud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fraud.mapper")
public class AntiFraudPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntiFraudPlatformApplication.class, args);
    }
}
