package com.recruitmatch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.recruitmatch.mapper")
public class RecruitMatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecruitMatchApplication.class, args);
    }
}
