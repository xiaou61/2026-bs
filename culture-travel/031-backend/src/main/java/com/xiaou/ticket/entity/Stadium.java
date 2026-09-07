package com.xiaou.ticket.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Stadium {
    private Long id;
    private String name;
    private String location;
    private String city;
    private Integer capacity;
    private String description;
    private String facilities;
    private String imageUrl;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
