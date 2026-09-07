package com.licensecheck;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.licensecheck.mapper")
public class LicenseComplianceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LicenseComplianceApplication.class, args);
    }
}
