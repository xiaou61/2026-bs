package com.property.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Building {
    private Long id;
    private String name;
    private Integer floors;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
