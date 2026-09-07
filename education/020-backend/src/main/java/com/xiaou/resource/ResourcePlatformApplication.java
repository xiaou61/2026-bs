package com.xiaou.resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaou.resource.mapper")
public class ResourcePlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourcePlatformApplication.class, args);
    }
}

