package com.textintegrity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.textintegrity.mapper")
public class TextIntegrityApplication {
    public static void main(String[] args) {
        SpringApplication.run(TextIntegrityApplication.class, args);
    }
}

