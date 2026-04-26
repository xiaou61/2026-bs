package com.hospital.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLog {
    private Long id;
    private Long userId;
    private String username;
    private String role;
    private String moduleName;
    private String actionType;
    private String description;
    private LocalDateTime createTime;
}
