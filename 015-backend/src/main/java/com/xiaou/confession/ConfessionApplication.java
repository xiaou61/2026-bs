package com.xiaou.confession;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaou.confession.mapper")
public class ConfessionApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfessionApplication.class, args);
    }
}

