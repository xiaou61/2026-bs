package com.xiaou.artist.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Review {
    private Long id;
    private Long orderId;
    private Long fromUserId;
    private Long toUserId;
    private Integer rating;
    private Integer qualityRating;
    private Integer attitudeRating;
    private Integer speedRating;
    private String content;
    private String tags;
    private LocalDateTime createTime;
    
    // 关联字段
    private String fromUsername;
    private String toUsername;
    private String orderTitle;
}
