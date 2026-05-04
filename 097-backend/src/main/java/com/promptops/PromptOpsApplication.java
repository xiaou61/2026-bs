package com.promptops;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.promptops.mapper")
@SpringBootApplication
public class PromptOpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromptOpsApplication.class, args);
    }
}
