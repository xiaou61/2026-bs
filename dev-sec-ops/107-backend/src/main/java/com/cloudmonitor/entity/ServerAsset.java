package com.cloudmonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("server_asset")
public class ServerAsset {
    @TableId(type = IdType.AUTO)
private Long id;
private String serverName;
private String instanceId;
private Long regionId;
private String publicIp;
private String privateIp;
private String osName;
private Integer cpuCores;
private Integer memoryGb;
private String ownerName;
private String status;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
