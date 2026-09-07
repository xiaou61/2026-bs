package com.groupbuy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.groupbuy.mapper")
@EnableScheduling
public class GroupBuyApplication {
    public static void main(String[] args) {
        SpringApplication.run(GroupBuyApplication.class, args);
    }
}
