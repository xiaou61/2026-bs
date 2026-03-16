package com.opera;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.opera.mapper")
@SpringBootApplication
public class OperaCultureApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperaCultureApplication.class, args);
    }
}


