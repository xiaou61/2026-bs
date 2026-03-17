package com.kindergarten.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CampusInfo {
    private Long id;
    private String name;
    private String code;
    private String deanName;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
