package com.promptops.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("evaluation_result")
public class EvaluationResult {
    private Long id;
    private Long taskId;
    private Long caseId;
    private String inputText;
    private String expectedOutput;
    private String actualOutput;
    private BigDecimal score;
    private Integer passed;
    private Integer reviewStatus;
    private String reviewComment;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String caseTitle;
    @TableField(exist = false)
    private String taskName;
}
