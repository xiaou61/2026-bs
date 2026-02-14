package com.fraud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("fraud_case")
public class FraudCase {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String caseNo;
    private Long alertId;
    private String caseType;
    private Integer status;
    private Integer priority;
    private Long ownerId;
    private String summary;
    private String result;
    private LocalDateTime closeTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
