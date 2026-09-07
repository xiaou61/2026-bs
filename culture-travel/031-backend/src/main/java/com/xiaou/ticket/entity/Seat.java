package com.xiaou.ticket.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Seat {
    private Long id;
    private Long categoryId;
    private String rowNumber;
    private String seatNumber;
    private String status;
    private LocalDateTime createdAt;
}
