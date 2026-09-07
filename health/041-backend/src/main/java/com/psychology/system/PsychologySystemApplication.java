package com.psychology.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.psychology.system.mapper")
public class PsychologySystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(PsychologySystemApplication.class, args);
    }
}
