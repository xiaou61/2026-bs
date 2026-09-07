package com.student.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notice {
    private Long id;
    private String title;
    private String content;
    private Long publisherId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
