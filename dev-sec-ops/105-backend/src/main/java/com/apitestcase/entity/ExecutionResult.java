package com.apitestcase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("execution_result")
public class ExecutionResult {
    @TableId(type = IdType.AUTO)
private Long id;
private Long executionId;
private Long caseId;
private Long endpointId;
private String resultStatus;
private Integer actualCode;
private Integer durationMs;
private String assertMessage;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
