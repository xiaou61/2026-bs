package com.knowledgeqa.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DocumentPermission {
    private Long id;
    private Long spaceId;
    private Long documentId;
    private Long groupId;
    private String permissionLevel;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
