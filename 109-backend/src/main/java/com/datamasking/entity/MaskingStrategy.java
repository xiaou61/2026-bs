package com.datamasking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("masking_strategy")
public class MaskingStrategy {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String strategyName;
    private String sensitiveType;
    private String maskingMethod;
    private String maskingExpression;
    private String reviewRole;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
