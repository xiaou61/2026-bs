package com.phishingtraining.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("phishing_campaign")
public class PhishingCampaign {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String campaignName;
    private String campaignNo;
    private String templateName;
    private String targetScope;
    private String ownerName;
    private LocalDateTime plannedTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
