package com.kindergarten.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SystemNotice {
    private Long id;
    private String title;
    private String content;
    private String type;
    private Integer status;
    private Long publisherId;
    private LocalDateTime publishTime;
    private Integer viewCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
