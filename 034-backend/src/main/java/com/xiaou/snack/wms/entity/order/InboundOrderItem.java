package com.xiaou.snack.wms.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("inbound_order_item")
public class InboundOrderItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long inboundId;
    private Long skuId;
    private String batchNo;
    private Integer quantity;
    private Long locationId;
    private LocalDate productionDate;
    private LocalDate expireDate;
}
