package com.xiaou.snack.wms.entity.inventory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("inventory_log")
public class InventoryLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long skuId;
    private Long batchId;
    private Long warehouseId;
    private Long locationId;
    private String changeType;
    private Integer changeQty;
    private String refType;
    private Long refId;
    private LocalDateTime createdAt;
}
