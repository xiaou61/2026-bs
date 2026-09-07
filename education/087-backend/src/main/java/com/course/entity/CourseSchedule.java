package com.course.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseSchedule {
    private Long id;
    private Long courseId;
    private Long termId;
    private Long teacherId;
    private Long classId;
    private String classroom;
    private String weekDay;
    private Integer startSection;
    private Integer endSection;
    private Integer maxStudentCount;
    private Integer selectedCount;
    private Integer status;
    private String courseName;
    private String teacherName;
    private String termName;
    private String className;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
