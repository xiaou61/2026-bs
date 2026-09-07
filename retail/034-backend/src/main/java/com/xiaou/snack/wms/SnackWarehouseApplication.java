package com.xiaou.snack.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SnackWarehouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(SnackWarehouseApplication.class, args);
    }
}
