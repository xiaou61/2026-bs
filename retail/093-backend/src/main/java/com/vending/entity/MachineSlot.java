package com.vending.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("machine_slot")
public class MachineSlot {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long machineId;
    private String slotNo;
    private Long productId;
    private Integer capacity;
    private Integer currentStock;
    private String status;
    private LocalDateTime lastSyncTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String machineName;
    @TableField(exist = false)
    private String productName;
}
