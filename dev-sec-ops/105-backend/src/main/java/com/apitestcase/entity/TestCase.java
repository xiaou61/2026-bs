package com.apitestcase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("test_case")
public class TestCase {
    @TableId(type = IdType.AUTO)
private Long id;
private Long endpointId;
private String caseName;
private String caseType;
private String priority;
private String requestBody;
private String expectedResult;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
