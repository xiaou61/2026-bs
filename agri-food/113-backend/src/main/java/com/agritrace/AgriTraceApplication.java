package com.agritrace;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.agritrace.mapper")
public class AgriTraceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgriTraceApplication.class, args);
    }
}
