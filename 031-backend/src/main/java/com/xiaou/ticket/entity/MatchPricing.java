package com.xiaou.ticket.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MatchPricing {
    private Long id;
    private Long matchId;
    private Long categoryId;
    private BigDecimal price;
    private Integer availableSeats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
