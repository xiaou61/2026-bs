package com.xiaou.snack.wms.controller;

import com.xiaou.snack.wms.entity.order.InboundOrder;
import com.xiaou.snack.wms.entity.order.InboundOrderItem;
import lombok.Data;

import java.util.List;

@Data
public class InboundRequest {
    private InboundOrder order;
    private List<InboundOrderItem> items;
}
