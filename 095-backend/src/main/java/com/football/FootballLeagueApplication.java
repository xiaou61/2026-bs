package com.football;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.football.mapper")
public class FootballLeagueApplication {
    public static void main(String[] args) {
        SpringApplication.run(FootballLeagueApplication.class, args);
    }
}

