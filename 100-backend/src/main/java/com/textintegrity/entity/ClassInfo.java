package com.textintegrity.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClassInfo {
    private Long id;
    private String className;
    private String major;
    private String grade;
    private String teacherName;
    private Integer studentCount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
