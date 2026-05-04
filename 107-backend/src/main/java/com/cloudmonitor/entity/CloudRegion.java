package com.cloudmonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("cloud_region")
public class CloudRegion {
    @TableId(type = IdType.AUTO)
private Long id;
private String regionName;
private String regionCode;
private String providerName;
private String locationName;
private String managerName;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
