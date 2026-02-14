package com.fraud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("risk_event")
public class RiskEvent {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String eventNo;
    private Long userId;
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
