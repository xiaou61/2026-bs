package com.textintegrity.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RectificationRecord {
    private Long id;
    private Long warningId;
    private Long studentId;
    private String revisionNote;
    private String revisionUrl;
    private Integer status;
    private String reviewComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
