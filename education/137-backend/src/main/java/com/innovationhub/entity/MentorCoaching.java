package com.innovationhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("purchase_approval")
public class MentorCoaching {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String approvalNo;
    private String requestNo;
    private String approverName;
    private String approvalOpinion;
    private String approvalTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


