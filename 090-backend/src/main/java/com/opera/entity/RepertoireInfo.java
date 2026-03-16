package com.opera.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RepertoireInfo {
    private Long id;
    private String courseName;
    private String courseCode;
    private Long departmentId;
    private Long artistId;
    private BigDecimal credit;
    private String courseType;
    private Integer courseHours;
    private String courseDesc;
    private Integer status;
    private String departmentName;
    private String artistName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}


