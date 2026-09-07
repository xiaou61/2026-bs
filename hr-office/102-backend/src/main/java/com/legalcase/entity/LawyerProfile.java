package com.legalcase.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LawyerProfile {
    private Long id;
    private Long userId;
    private String realName;
    private String licenseNo;
    private String speciality;
    private Integer experienceYears;
    private Integer serviceStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
