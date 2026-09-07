package com.devopsrelease;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.devopsrelease.mapper")
public class DevopsReleaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevopsReleaseApplication.class, args);
    }
}
