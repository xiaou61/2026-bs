package com.phishingtraining.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("click_tracking")
public class ClickTracking {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String trackNo;
    private String campaignName;
    private String employeeName;
    private String clientIp;
    private String submitCredential;
    private LocalDateTime clickTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
