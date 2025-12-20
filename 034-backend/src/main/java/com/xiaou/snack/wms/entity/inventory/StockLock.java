package com.xiaou.snack.wms.entity.inventory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("stock_lock")
public class StockLock {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long skuId;
    private Long batchId;
    private Long warehouseId;
    private Long locationId;
    private Integer quantity;
    private String status;
    private LocalDateTime createdAt;
}
