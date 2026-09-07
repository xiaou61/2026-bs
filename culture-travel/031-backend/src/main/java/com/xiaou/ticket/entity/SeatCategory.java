package com.xiaou.ticket.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SeatCategory {
    private Long id;
    private Long stadiumId;
    private String name;
    private String description;
    private Integer totalSeats;
    private Integer rowCount;
    private Integer columnCount;
    private LocalDateTime createdAt;
}
