package com.teacher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.teacher.mapper")
@SpringBootApplication
public class NearbyTravelApplication {
    public static void main(String[] args) {
        SpringApplication.run(NearbyTravelApplication.class, args);
    }
}

