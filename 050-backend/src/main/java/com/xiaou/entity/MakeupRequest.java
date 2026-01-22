package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("makeup_request")
public class MakeupRequest {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long studentId;
    private Long taskId;
    private String reason;
    private String attachment;
    private Integer status; // 0-待审核 1-已批准 2-已拒绝
    private LocalDateTime approveTime;
    private String approveRemark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
    
    @TableField(exist = false)
    private String studentName;
    @TableField(exist = false)
    private String taskTitle;
}
