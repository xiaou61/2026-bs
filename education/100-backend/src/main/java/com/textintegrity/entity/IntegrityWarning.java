package com.textintegrity.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IntegrityWarning {
    private Long id;
    private Long resultId;
    private Long studentId;
    private String warningLevel;
    private String warningTitle;
    private String warningContent;
    private Integer status;
    private Long handlerId;
    private String handleComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
