package com.teachres.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialTag {
    private Long id;
    private String name;
    private String color;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
