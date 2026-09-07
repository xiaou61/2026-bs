package com.online;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.online.mapper")
public class OnlineCourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineCourseApplication.class, args);
    }
}
