package com.textintegrity.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AssignmentTask {
    private Long id;
    private Long courseId;
    private String title;
    private String assignmentType;
    private Long teacherId;
    private LocalDateTime deadline;
    private BigDecimal thresholdScore;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
