package com.knowledgeqa.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KnowledgeDocument {
    private Long id;
    private Long spaceId;
    private Long categoryId;
    private String title;
    private String summary;
    private String sourceType;
    private String tags;
    private String content;
    private Integer status;
    private Long creatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
