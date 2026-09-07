package com.disaster;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.disaster.mapper")
public class DisasterApplication {
    public static void main(String[] args) {
        SpringApplication.run(DisasterApplication.class, args);
    }
}
