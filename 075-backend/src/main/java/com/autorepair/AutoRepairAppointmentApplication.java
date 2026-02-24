package com.autorepair;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.autorepair.mapper")
public class AutoRepairAppointmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AutoRepairAppointmentApplication.class, args);
    }
}


