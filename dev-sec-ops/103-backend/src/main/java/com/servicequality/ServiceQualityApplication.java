package com.servicequality;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.servicequality.mapper")
public class ServiceQualityApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceQualityApplication.class, args);
    }
}
