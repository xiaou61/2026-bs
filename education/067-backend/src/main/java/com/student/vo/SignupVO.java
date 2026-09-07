package com.student.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignupVO {
    private Long id;
    private Long activityId;
    private String activityTitle;
    private Long studentId;
    private String studentName;
    private Integer status;
    private LocalDateTime checkinTime;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
