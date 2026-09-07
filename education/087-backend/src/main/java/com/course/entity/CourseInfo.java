package com.course.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CourseInfo {
    private Long id;
    private String courseName;
    private String courseCode;
    private Long departmentId;
    private Long teacherId;
    private BigDecimal credit;
    private String courseType;
    private Integer courseHours;
    private String courseDesc;
    private Integer status;
    private String departmentName;
    private String teacherName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
