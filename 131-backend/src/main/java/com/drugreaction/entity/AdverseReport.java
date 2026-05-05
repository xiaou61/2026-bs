package com.drugreaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("adverse_report")
public class AdverseReport {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String reportNo;
    private String patientNo;
    private String drugName;
    private String reportTime;
    private String severityLevel;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
