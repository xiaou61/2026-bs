package com.student.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CourseEnroll {
    private Long id;
    private Long courseId;
    private Long studentId;
    private Integer status;
    private BigDecimal score;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
