package com.textintegrity.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetectionTask {
    private Long id;
    private String taskNo;
    private Long submissionId;
    private String taskName;
    private String priority;
    private Integer status;
    private Long reviewerId;
    private LocalDateTime createTime;
    private LocalDateTime finishTime;
}
