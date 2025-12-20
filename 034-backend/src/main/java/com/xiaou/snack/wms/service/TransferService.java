package com.xiaou.snack.wms.service;

import com.xiaou.snack.wms.entity.order.TransferOrder;
import com.xiaou.snack.wms.entity.order.TransferOrderItem;

import java.util.List;

public interface TransferService {
    void createTransfer(TransferOrder order, List<TransferOrderItem> items);
}
