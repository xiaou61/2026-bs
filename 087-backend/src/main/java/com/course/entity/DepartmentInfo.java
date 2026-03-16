package com.course.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepartmentInfo {
    private Long id;
    private String name;
    private String code;
    private String deanName;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
