package com.milk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.milk.mapper")
@EnableScheduling
public class MilkApplication {
    public static void main(String[] args) {
        SpringApplication.run(MilkApplication.class, args);
    }
}
