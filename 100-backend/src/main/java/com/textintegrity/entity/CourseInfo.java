package com.textintegrity.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseInfo {
    private Long id;
    private String courseName;
    private String courseCode;
    private String teacherName;
    private String semester;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
