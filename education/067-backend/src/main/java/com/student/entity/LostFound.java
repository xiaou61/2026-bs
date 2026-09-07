package com.student.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LostFound {
    private Long id;
    private Integer type;
    private String title;
    private String itemName;
    private String contact;
    private String location;
    private LocalDateTime happenTime;
    private String description;
    private String imageUrl;
    private Integer status;
    private Long publisherId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
