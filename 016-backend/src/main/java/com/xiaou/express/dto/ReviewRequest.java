package com.xiaou.express.dto;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long orderId;
    private Integer rating;
    private String tags;
    private String content;
    private Integer isAnonymous;
}

