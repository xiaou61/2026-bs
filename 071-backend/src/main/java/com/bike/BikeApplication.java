package com.bike;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bike.mapper")
public class BikeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BikeApplication.class, args);
    }
}
