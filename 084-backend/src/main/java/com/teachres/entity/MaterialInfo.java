package com.teachres.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialInfo {
    private Long id;
    private String title;
    private String summary;
    private Long categoryId;
    private String grade;
    private String subject;
    private String keyword;
    private Long uploaderId;
    private Integer auditStatus;
    private Integer publishStatus;
    private Integer downloadCount;
    private Integer favoriteCount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
