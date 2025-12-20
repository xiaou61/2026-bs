package com.xiaou.snack.wms.entity.inventory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("inventory")
public class Inventory {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long skuId;
    private Long warehouseId;
    private Long locationId;
    private Long batchId;
    private Integer quantityAvailable;
    private Integer quantityLocked;
    private Integer quantityFrozen;
}
