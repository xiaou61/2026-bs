package com.craft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.craft.mapper")
public class CraftSalesApplication {
    public static void main(String[] args) {
        SpringApplication.run(CraftSalesApplication.class, args);
    }
}

