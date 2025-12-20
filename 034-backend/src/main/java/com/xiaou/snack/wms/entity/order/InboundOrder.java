package com.xiaou.snack.wms.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("inbound_order")
public class InboundOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long warehouseId;
    private Long supplierId;
    private String status;
    private LocalDateTime createdAt;
}
