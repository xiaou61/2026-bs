package com.xiaou.snack.wms.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("outbound_order")
public class OutboundOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long warehouseId;
    private Long customerId;
    private String status;
    private LocalDateTime createdAt;
}
