package com.servicequality.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@TableName("quality_rule")
public class QualityRule {
    @TableId
    private Long id;
    private String ruleName;
    private String ruleType;
    private String keyword;
    private BigDecimal deductScore;
    private Integer status;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
