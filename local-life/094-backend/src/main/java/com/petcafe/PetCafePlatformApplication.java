package com.petcafe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.petcafe.mapper")
public class PetCafePlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetCafePlatformApplication.class, args);
    }
}
