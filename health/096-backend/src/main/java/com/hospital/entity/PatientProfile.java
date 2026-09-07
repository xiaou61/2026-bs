package com.hospital.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PatientProfile {
    private Long id;
    private Long userId;
    private String realName;
    private String idCard;
    private String gender;
    private Integer age;
    private String bloodType;
    private String allergyHistory;
    private String emergencyContact;
    private String emergencyPhone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
