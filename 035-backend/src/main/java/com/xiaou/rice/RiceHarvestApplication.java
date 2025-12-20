package com.xiaou.rice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaou.rice.**.mapper")
public class RiceHarvestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RiceHarvestApplication.class, args);
    }
}
