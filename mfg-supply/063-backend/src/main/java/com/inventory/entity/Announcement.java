package com.inventory.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Announcement {
    private Long id;
    private String title;
    private String content;
    private Long adminId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
