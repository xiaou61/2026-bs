package com.course.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseSelection {
    private Long id;
    private Long scheduleId;
    private Long courseId;
    private Long studentId;
    private Integer selectStatus;
    private String courseName;
    private String studentName;
    private String scheduleInfo;
    private LocalDateTime createTime;
}
