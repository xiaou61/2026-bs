package com.student.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivitySignup {
    private Long id;
    private Long activityId;
    private Long studentId;
    private Integer status;
    private LocalDateTime checkinTime;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
