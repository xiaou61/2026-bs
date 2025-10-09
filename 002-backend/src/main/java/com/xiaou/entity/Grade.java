package com.xiaou.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Grade {
    private Long id;
    private Long studentId;
    private String studentName;
    private String studentNo;
    private Long courseId;
    private String courseName;
    private String courseNo;
    private Long teacherId;
    private String teacherName;
    private BigDecimal credit;
    private BigDecimal score;
    private BigDecimal gpa;
    private String semester;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime submitTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedTime;
}

