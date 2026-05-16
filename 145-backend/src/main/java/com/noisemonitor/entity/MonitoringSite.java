package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("monitoring_site")
public class MonitoringSite {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String siteNo;
    private String siteName;
    private String siteType;
    private String streetName;
    private String noiseThreshold;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
