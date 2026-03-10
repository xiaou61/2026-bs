package com.teachres.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLog {
    private Long id;
    private Long userId;
    private String module;
    private String action;
    private String content;
    private LocalDateTime createTime;
}
