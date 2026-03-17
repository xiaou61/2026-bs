package com.vending.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("shipment_record")
public class ShipmentRecord {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long userId;
    private Long machineId;
    private Long slotId;
    private Long productId;
    private Integer quantity;
    private String shipmentStatus;
    private LocalDateTime shipmentTime;
    private String resultMsg;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String orderNo;
    @TableField(exist = false)
    private String machineName;
    @TableField(exist = false)
    private String slotNo;
    @TableField(exist = false)
    private String productName;
}
