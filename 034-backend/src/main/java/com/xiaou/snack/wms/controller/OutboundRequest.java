package com.xiaou.snack.wms.controller;

import com.xiaou.snack.wms.entity.order.OutboundOrder;
import com.xiaou.snack.wms.entity.order.OutboundOrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OutboundRequest {
    private OutboundOrder order;
    private List<OutboundOrderItem> items;
}
