package com.gongkao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gongkao.mapper")
public class GongkaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(GongkaoApplication.class, args);
    }
}
