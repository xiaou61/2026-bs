package com.labconsumable.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.labconsumable.entity.PurchaseOrder;
import com.labconsumable.mapper.PurchaseOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService extends BaseCrudService<PurchaseOrder> {
    private final PurchaseOrderMapper purchaseOrderMapper;

    @Override
    protected BaseMapper<PurchaseOrder> mapper() {
        return purchaseOrderMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"order_no", "supplier_name", "consumable_name", "order_amount"};
    }
}
