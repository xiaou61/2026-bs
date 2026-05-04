package com.promptops.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("operation_log")
public class OperationLog {
    private Long id;
    private Long userId;
    private String username;
    private String role;
    private String moduleName;
    private String actionType;
    private String description;
    private LocalDateTime createTime;
}
