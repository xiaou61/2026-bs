package com.student.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobApply {
    private Long id;
    private Long jobId;
    private Long studentId;
    private String resumeUrl;
    private Integer status;
    private String applyNote;
    private String reviewNote;
    private Long reviewerId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
