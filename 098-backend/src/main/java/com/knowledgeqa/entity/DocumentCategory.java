package com.knowledgeqa.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentCategory {
    private Long id;
    private Long spaceId;
    private String name;
    private String code;
    private String description;
    private Integer sort;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
