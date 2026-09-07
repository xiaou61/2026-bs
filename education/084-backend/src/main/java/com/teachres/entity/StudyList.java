package com.teachres.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudyList {
    private Long id;
    private Long userId;
    private Long materialId;
    private Integer progress;
    private String note;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
