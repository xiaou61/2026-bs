package com.kindergarten.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClassInfo {
    private Long id;
    private Long majorId;
    private String name;
    private String counselorName;
    private Integer studentCount;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
