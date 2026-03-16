package com.opera.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MediaResource {
    private Long id;
    private Long scheduleId;
    private Long courseId;
    private Long artistId;
    private String title;
    private String resourceType;
    private String resourceUrl;
    private String contentDesc;
    private Integer status;
    private String courseName;
    private String artistName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}


