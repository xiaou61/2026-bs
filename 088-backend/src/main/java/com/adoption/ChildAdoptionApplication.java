package com.adoption;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.adoption.mapper")
public class ChildAdoptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChildAdoptionApplication.class, args);
    }
}
