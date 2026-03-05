package com.alumni;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.alumni.mapper")
public class AlumniApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlumniApplication.class, args);
    }
}
