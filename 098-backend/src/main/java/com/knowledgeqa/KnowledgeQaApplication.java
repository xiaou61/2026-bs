package com.knowledgeqa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.knowledgeqa.mapper")
public class KnowledgeQaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KnowledgeQaApplication.class, args);
    }
}
