package com.innovationhub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("inventory_check")
public class MilestoneTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String checkNo;
    private String labName;
    private String consumableName;
    private Integer bookQty;
    private Integer actualQty;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}


