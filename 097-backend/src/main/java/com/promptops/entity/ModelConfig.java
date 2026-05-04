package com.promptops.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("model_config")
public class ModelConfig {
    private Long id;
    private String name;
    private String provider;
    private String modelName;
    private BigDecimal temperature;
    private Integer maxTokens;
    private Integer isDefault;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
