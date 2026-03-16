package com.opera.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReviewRecord {
    private Long id;
    private Long selectionId;
    private Long scheduleId;
    private Long courseId;
    private Long memberId;
    private Long artistId;
    private BigDecimal usualScore;
    private BigDecimal examScore;
    private BigDecimal totalScore;
    private String gradeLevel;
    private String remark;
    private String courseName;
    private String memberName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}


