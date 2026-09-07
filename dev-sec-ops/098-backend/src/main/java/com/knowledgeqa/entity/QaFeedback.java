package com.knowledgeqa.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QaFeedback {
    private Long id;
    private Long recordId;
    private Long userId;
    private String content;
    private Integer score;
    private Integer status;
    private String replyContent;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
