package com.game.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewVO {
    private Long id;
    private Long orderId;
    private Long userId;
    private Long itemId;
    private Integer rating;
    private String content;
    private Integer status;
    private LocalDateTime createTime;
    private String userName;
}
