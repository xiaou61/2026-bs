package com.xiaou.snack.wms.service;

import com.xiaou.snack.wms.entity.order.OutboundOrder;
import com.xiaou.snack.wms.entity.order.OutboundOrderItem;

import java.util.List;

public interface OutboundService {
    void createOutbound(OutboundOrder order, List<OutboundOrderItem> items);
}
