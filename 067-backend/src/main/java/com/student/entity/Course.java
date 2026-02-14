package com.student.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Course {
    private Long id;
    private String name;
    private Long teacherId;
    private BigDecimal credit;
    private String location;
    private Integer maxStudent;
    private Integer selectedCount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
