package com.course.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseEvaluation {
    private Long id;
    private Long scheduleId;
    private Long courseId;
    private Long studentId;
    private Long teacherId;
    private Integer evaluationScore;
    private String evaluationContent;
    private String courseName;
    private String studentName;
    private LocalDateTime createTime;
}
