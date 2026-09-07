package com.servicequality.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@TableName("agent_performance")
public class AgentPerformance {
    @TableId
    private Long id;
    private Long agentId;
    private String statDate;
    private Integer orderCount;
    private Integer resolvedCount;
    private BigDecimal avgQualityScore;
    private BigDecimal satisfactionScore;
    private String rankLevel;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
