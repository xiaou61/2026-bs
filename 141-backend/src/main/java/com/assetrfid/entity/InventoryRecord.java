package com.assetrfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("inventory_record")
public class InventoryRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String recordNo;
    private String taskNo;
    private String checkerName;
    private String differenceNote;
    private String checkTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
