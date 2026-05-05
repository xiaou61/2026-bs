package com.drugreaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("risk_assessment")
public class RiskAssessment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String assessNo;
    private String reportNo;
    private String assessLevel;
    private BigDecimal scoreValue;
    private String assessorName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
