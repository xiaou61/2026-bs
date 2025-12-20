package com.xiaou.snack.wms.entity.inventory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("stock_check_item")
public class StockCheckItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long checkId;
    private Long skuId;
    private Long batchId;
    private Long locationId;
    private Integer systemQty;
    private Integer countedQty;
    private Integer diffQty;
}
