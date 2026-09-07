package com.droneinspect.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("rectification_order")
public class RectificationOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private String defectNo;
    private String responsibleTeam;
    private String deadlineTime;
    private String rectifyMeasure;
    private String verifyResult;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
