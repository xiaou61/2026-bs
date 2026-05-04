package com.apitestcase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("test_execution")
public class TestExecution {
    @TableId(type = IdType.AUTO)
private Long id;
private String executionNo;
private Long projectId;
private String envName;
private String executor;
private String status;
private String startedAt;
private String finishedAt;
private String summary;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
