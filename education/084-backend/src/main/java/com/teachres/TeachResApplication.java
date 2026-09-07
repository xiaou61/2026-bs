package com.teachres;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.teachres.mapper")
@SpringBootApplication
public class TeachResApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachResApplication.class, args);
    }
}
