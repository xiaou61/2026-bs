package com.xiaou.snack.wms.controller;

import com.xiaou.snack.wms.entity.order.TransferOrder;
import com.xiaou.snack.wms.entity.order.TransferOrderItem;
import lombok.Data;

import java.util.List;

@Data
public class TransferRequest {
    private TransferOrder order;
    private List<TransferOrderItem> items;
}
