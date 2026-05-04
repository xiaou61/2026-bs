package com.aigccopyright.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("audit_rule")
public class AuditRule {
    private Long id;
    private String ruleName;
    private String ruleType;
    private String riskLevel;
    private String keywords;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
