package com.xiaou.snack.wms.service;

import com.xiaou.snack.wms.entity.order.InboundOrder;
import com.xiaou.snack.wms.entity.order.InboundOrderItem;

import java.util.List;

public interface InboundService {
    void createInbound(InboundOrder order, List<InboundOrderItem> items);
}
