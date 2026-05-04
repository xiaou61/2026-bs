package com.coldchain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.coldchain.mapper")
public class ColdChainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ColdChainApplication.class, args);
    }
}
