package com.phishingtraining.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("campaign_target")
public class CampaignTarget {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String campaignName;
    private String employeeName;
    private String email;
    private String departmentName;
    private String riskTag;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
