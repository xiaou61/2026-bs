package com.teacher.new;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.teacher.new.mapper")
@SpringBootApplication(scanBasePackages = "com.teacher.new")
public class TeacherEvaluationApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeacherEvaluationApplication.class, args);
    }
}
