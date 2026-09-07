package com.sparelife.entity;

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
    private String operatorName;
    private String moduleName;
    private String actionType;
    private String targetName;
    private String detail;
    private String ipAddress;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
