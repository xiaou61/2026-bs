package com.psychologycare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("follow_up_plan")
public class FollowUpPlan {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String followUpNo;
    private String caseTheme;
    private String followUpStage;
    private String followUpContent;
    private String plannedTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}







