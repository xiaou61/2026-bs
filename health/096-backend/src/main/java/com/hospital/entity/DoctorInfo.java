package com.hospital.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DoctorInfo {
    private Long id;
    private Long userId;
    private Long departmentId;
    private String doctorName;
    private String title;
    private String gender;
    private String specialty;
    private String introduction;
    private String avatar;
    private BigDecimal consultationFee;
    private Integer status;
    private Integer visitCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private String departmentName;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String nickname;
}
