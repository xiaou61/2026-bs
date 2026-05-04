package com.knowledgeqa.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QaSession {
    private Long id;
    private String sessionNo;
    private String title;
    private Long spaceId;
    private Long userId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime closeTime;
}
