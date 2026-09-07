package com.apitestcase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("test_step")
public class TestStep {
    @TableId(type = IdType.AUTO)
private Long id;
private Long caseId;
private String stepName;
private Integer stepOrder;
private String requestData;
private String assertExpression;
private String extractVariable;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
