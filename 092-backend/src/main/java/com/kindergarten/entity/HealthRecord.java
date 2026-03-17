package com.kindergarten.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class HealthRecord {
    private Long id;
    private Long childId;
    private Long teacherId;
    private LocalDate recordDate;
    private BigDecimal temperature;
    private String healthStatus;
    private String emotionStatus;
    private String attendanceAdvice;
    private String remark;
    private String childName;
    private String teacherName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
