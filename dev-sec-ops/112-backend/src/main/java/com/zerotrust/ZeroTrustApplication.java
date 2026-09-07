package com.zerotrust;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zerotrust.mapper")
public class ZeroTrustApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZeroTrustApplication.class, args);
    }
}
