package com.kindergarten.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PickupRecord {
    private Long id;
    private Long userId;
    private String module;
    private String action;
    private String content;
    private LocalDateTime createTime;
}
