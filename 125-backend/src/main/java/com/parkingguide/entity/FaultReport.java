package com.parkingguide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("fault_report")
public class FaultReport {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String faultNo;
    private String targetName;
    private String faultType;
    private String severityLevel;
    private String reporterName;
    private String reportTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
