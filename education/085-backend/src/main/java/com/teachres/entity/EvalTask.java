package com.teachres.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EvalTask {
    private Long id;
    private String taskName;
    private Long courseId;
    private String term;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer status;
    private Long creatorId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
