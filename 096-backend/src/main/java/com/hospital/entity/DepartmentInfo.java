package com.hospital.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepartmentInfo {
    private Long id;
    private String name;
    private String code;
    private String description;
    private String location;
    private String phone;
    private Integer sort;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
