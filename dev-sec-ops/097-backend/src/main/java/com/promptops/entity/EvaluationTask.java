package com.promptops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("evaluation_task")
public class EvaluationTask {
    private Long id;
    private String taskNo;
    private String name;
    private Long assetId;
    private Long versionId;
    private Long modelId;
    private Integer status;
    private BigDecimal averageScore;
    private BigDecimal passRate;
    private Long creatorId;
    private LocalDateTime createTime;
    private LocalDateTime finishTime;
    @TableField(exist = false)
    private String assetTitle;
    @TableField(exist = false)
    private String versionNo;
    @TableField(exist = false)
    private String modelName;
    @TableField(exist = false)
    private String creatorName;
}
