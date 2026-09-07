package com.textintegrity.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudentProfile {
    private Long id;
    private String studentNo;
    private String name;
    private Long classId;
    private String major;
    private String grade;
    private String phone;
    private String email;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
