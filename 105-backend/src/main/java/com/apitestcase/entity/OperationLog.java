package com.apitestcase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("operation_log")
public class OperationLog {
    @TableId(type = IdType.AUTO)
private Long id;
private String username;
private String action;
private String targetType;
private Long targetId;
private String detail;
private String ipAddress;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
