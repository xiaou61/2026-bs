package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("warning_rule")
public class WarningRule {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String ruleNo;
    private String complaintTitle;
    private String warningMetric;
    private String thresholdConfig;
    private String effectiveTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






