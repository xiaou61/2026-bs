package com.sparelife.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("usage_record")
public class UsageRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String usageNo;
    private String partName;
    private String equipmentName;
    private String installTime;
    private Integer runtimeHours;
    private String installerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
