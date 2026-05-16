package com.assetrfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("inventory_task")
public class InventoryTask {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String taskNo;
    private String assetName;
    private Integer plannedCount;
    private String executorName;
    private String taskTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
