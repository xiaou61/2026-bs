package com.xiaou.ticket.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Team {
    private Long id;
    private String name;
    private String logoUrl;
    private String country;
    private String city;
    private Integer foundedYear;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
