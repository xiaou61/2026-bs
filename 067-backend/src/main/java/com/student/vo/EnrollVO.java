package com.student.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EnrollVO {
    private Long id;
    private Long courseId;
    private String courseName;
    private String teacherName;
    private Long studentId;
    private String studentName;
    private Integer status;
    private BigDecimal score;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
