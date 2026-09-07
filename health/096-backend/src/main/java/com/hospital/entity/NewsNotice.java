package com.hospital.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsNotice {
    private Long id;
    private String title;
    private String content;
    private Integer status;
    private Integer sort;
    private Long adminId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
