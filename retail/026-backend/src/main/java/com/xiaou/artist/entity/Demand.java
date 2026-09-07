package com.xiaou.artist.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Demand {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String type;
    private String size;
    private String style;
    private BigDecimal budgetMin;
    private BigDecimal budgetMax;
    private LocalDate deadline;
    private String refImages;
    private Long targetArtistId;
    private String status;  // OPEN/BIDDING/ACCEPTED/CLOSED
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    // 关联字段
    private String username;
    private String targetArtistName;
}
