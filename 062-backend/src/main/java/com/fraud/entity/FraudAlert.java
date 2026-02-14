package com.fraud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("fraud_alert")
public class FraudAlert {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String alertNo;
    private Long eventId;
    private Long userId;
    private Integer riskScore;
    private String riskLevel;
    private Integer status;
    private Long assigneeId;
    private String handleResult;
    private String handleRemark;
    private LocalDateTime handleTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
