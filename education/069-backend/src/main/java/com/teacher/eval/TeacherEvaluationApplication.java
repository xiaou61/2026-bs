package com.teacher.eval;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.teacher.eval.mapper")
@SpringBootApplication(scanBasePackages = "com.teacher.eval")
public class TeacherEvaluationApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeacherEvaluationApplication.class, args);
    }
}

