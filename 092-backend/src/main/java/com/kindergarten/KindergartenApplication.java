package com.kindergarten;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.kindergarten.mapper")
@SpringBootApplication
public class KindergartenApplication {

    public static void main(String[] args) {
        SpringApplication.run(KindergartenApplication.class, args);
    }
}
