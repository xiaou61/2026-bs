package com.fraud.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FraudAlertVO {
    private Long id;
    private String alertNo;
    private Long eventId;
    private String eventNo;
    private Long userId;
    private String userName;
    private Integer riskScore;
    private String riskLevel;
    private Integer status;
    private Long assigneeId;
    private String assigneeName;
    private String handleResult;
    private String handleRemark;
    private LocalDateTime handleTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
