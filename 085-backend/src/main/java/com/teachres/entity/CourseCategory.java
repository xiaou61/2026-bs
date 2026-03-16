package com.teachres.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseCategory {
    private Long id;
    private Long parentId;
    private String name;
    private String code;
    private Integer sort;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
