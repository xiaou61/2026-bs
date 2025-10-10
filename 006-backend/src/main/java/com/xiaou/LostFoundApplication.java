package com.xiaou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaou.mapper")
public class LostFoundApplication {
    public static void main(String[] args) {
        SpringApplication.run(LostFoundApplication.class, args);
    }
}

