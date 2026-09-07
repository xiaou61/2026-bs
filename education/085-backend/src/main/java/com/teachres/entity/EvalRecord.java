package com.teachres.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EvalRecord {
    private Long id;
    private Long taskId;
    private Long courseId;
    private Long studentId;
    private BigDecimal totalScore;
    private String commentContent;
    private LocalDateTime submitTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
