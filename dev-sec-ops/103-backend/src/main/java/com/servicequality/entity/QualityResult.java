package com.servicequality.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@TableName("quality_result")
public class QualityResult {
    @TableId
    private Long id;
    private Long taskId;
    private Long orderId;
    private Long agentId;
    private BigDecimal score;
    private String riskLevel;
    private String hitRules;
    private String suggestion;
    private Integer reviewStatus;
    private String reviewComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
