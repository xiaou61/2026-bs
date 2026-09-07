package com.servicequality.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("operation_log")
public class OperationLog {
    @TableId
    private Long id;
    private Long userId;
    private String username;
    private String role;
    private String moduleName;
    private String actionType;
    private String description;
    private LocalDateTime createTime;
}
