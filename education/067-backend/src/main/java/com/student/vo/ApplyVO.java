package com.student.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplyVO {
    private Long id;
    private Long jobId;
    private String jobTitle;
    private String company;
    private Long studentId;
    private String studentName;
    private String resumeUrl;
    private Integer status;
    private String applyNote;
    private String reviewNote;
    private Long reviewerId;
    private String reviewerName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
