package com.crossborder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("logistics_track")
public class LogisticsTrack {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String trackingNo;
    private String orderNo;
    private String carrierName;
    private String currentNode;
    private String locationText;
    private String trackTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
