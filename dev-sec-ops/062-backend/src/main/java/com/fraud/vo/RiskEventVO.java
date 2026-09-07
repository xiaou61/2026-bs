package com.fraud.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RiskEventVO {
    private Long id;
    private String eventNo;
    private Long userId;
    private String userName;
    private String accountNo;
    private String deviceId;
    private String ip;
    private BigDecimal amount;
    private String eventType;
    private String channel;
    private Integer riskScore;
    private String riskLevel;
    private Integer status;
    private String hitRules;
    private String remark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
