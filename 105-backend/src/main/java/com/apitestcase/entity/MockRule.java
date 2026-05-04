package com.apitestcase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mock_rule")
public class MockRule {
    @TableId(type = IdType.AUTO)
private Long id;
private Long endpointId;
private String ruleName;
private String matchExpression;
private Integer responseCode;
private String responseBody;
private Integer delayMs;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
