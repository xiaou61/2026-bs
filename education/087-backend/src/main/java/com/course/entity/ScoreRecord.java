package com.course.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ScoreRecord {
    private Long id;
    private Long selectionId;
    private Long scheduleId;
    private Long courseId;
    private Long studentId;
    private Long teacherId;
    private BigDecimal usualScore;
    private BigDecimal examScore;
    private BigDecimal totalScore;
    private String gradeLevel;
    private String remark;
    private String courseName;
    private String studentName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
