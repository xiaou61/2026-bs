package com.agriculture;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.agriculture.mapper")
public class AgricultureApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgricultureApplication.class, args);
    }
}
