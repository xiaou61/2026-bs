package com.ticket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ticket.mapper")
public class TicketSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketSystemApplication.class, args);
    }
}
