package com.hospital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.hospital.mapper")
@SpringBootApplication
public class OnlineHospitalRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineHospitalRegistrationApplication.class, args);
    }
}
