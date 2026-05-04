package com.legalcase.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClientProfile {
    private Long id;
    private Long userId;
    private String realName;
    private String idNo;
    private String phone;
    private String address;
    private String casePreference;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
