package com.xiaou.snack.wms.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("outbound_order_item")
public class OutboundOrderItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long outboundId;
    private Long skuId;
    private Long batchId;
    private Integer quantity;
    private Long locationId;
}
