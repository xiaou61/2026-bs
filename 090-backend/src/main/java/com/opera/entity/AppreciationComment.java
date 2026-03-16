package com.opera.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppreciationComment {
    private Long id;
    private Long scheduleId;
    private Long courseId;
    private Long memberId;
    private Long artistId;
    private Integer evaluationScore;
    private String evaluationContent;
    private String courseName;
    private String memberName;
    private LocalDateTime createTime;
}


