package com.vending.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("replenish_record")
public class ReplenishRecord {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String replenishNo;
    private Long machineId;
    private Long slotId;
    private Long productId;
    private Integer quantity;
    private Integer beforeStock;
    private Integer afterStock;
    private Long operatorId;
    private String remark;
    private LocalDateTime createTime;
    @TableField(exist = false)
    private String machineName;
    @TableField(exist = false)
    private String slotNo;
    @TableField(exist = false)
    private String productName;
    @TableField(exist = false)
    private String operatorName;
}
