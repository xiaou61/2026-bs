package com.kindergarten.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeeklyRecipe {
    private Long id;
    private Long scheduleId;
    private Long courseId;
    private Long teacherId;
    private String title;
    private String resourceType;
    private String resourceUrl;
    private String contentDesc;
    private Integer status;
    private String courseName;
    private String teacherName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
