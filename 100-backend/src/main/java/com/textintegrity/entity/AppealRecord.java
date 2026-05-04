package com.textintegrity.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppealRecord {
    private Long id;
    private String targetType;
    private Long targetId;
    private Long studentId;
    private String reason;
    private Integer status;
    private String handleComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
