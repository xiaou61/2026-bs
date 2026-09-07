package com.kindergarten.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SchoolTerm {
    private Long id;
    private String termName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer currentFlag;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
