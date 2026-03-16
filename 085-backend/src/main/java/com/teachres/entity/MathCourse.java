package com.teachres.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MathCourse {
    private Long id;
    private String courseName;
    private String courseCode;
    private Long categoryId;
    private Long teacherId;
    private BigDecimal credit;
    private String term;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
