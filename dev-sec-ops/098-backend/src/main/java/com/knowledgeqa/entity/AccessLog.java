package com.knowledgeqa.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessLog {
    private Long id;
    private Long userId;
    private String username;
    private String role;
    private String moduleName;
    private String actionType;
    private String description;
    private LocalDateTime createTime;
}
