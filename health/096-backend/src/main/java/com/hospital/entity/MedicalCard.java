package com.hospital.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicalCard {
    private Long id;
    private Long userId;
    private String cardNo;
    private String patientName;
    private String phone;
    private String idCard;
    private Integer isDefault;
    private Integer status;
    private LocalDateTime createTime;
}
