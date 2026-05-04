package com.cloudcost;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cloudcost.mapper")
public class CloudCostApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudCostApplication.class, args);
    }
}
