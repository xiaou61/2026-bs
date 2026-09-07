package com.legalcase;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.legalcase.mapper")
public class LegalCaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(LegalCaseApplication.class, args);
    }
}
