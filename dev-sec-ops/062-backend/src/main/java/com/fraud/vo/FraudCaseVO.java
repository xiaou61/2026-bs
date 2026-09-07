package com.fraud.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FraudCaseVO {
    private Long id;
    private String caseNo;
    private Long alertId;
    private String alertNo;
    private String caseType;
    private Integer status;
    private Integer priority;
    private Long ownerId;
    private String ownerName;
    private String summary;
    private String result;
    private LocalDateTime closeTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
