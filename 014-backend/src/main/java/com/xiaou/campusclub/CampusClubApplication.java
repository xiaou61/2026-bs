package com.xiaou.campusclub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaou.campusclub.mapper")
public class CampusClubApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusClubApplication.class, args);
    }
}

