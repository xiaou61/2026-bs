package com.vending;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.vending.mapper")
public class VendingManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(VendingManagementApplication.class, args);
    }
}
