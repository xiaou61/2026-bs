package com.xiaou.snack.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.snack.wms.common.ApiException;
import com.xiaou.snack.wms.entity.inventory.Inventory;
import com.xiaou.snack.wms.entity.inventory.InventoryLog;
import com.xiaou.snack.wms.entity.order.OutboundOrder;
import com.xiaou.snack.wms.entity.order.OutboundOrderItem;
import com.xiaou.snack.wms.mapper.InventoryLogMapper;
import com.xiaou.snack.wms.mapper.OutboundOrderItemMapper;
import com.xiaou.snack.wms.mapper.OutboundOrderMapper;
import com.xiaou.snack.wms.service.InventoryService;
import com.xiaou.snack.wms.service.OutboundService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OutboundServiceImpl extends ServiceImpl<OutboundOrderMapper, OutboundOrder> implements OutboundService {
    private final OutboundOrderItemMapper itemMapper;
    private final InventoryService inventoryService;
    private final InventoryLogMapper inventoryLogMapper;

    public OutboundServiceImpl(OutboundOrderItemMapper itemMapper, InventoryService inventoryService, InventoryLogMapper inventoryLogMapper) {
        this.itemMapper = itemMapper;
        this.inventoryService = inventoryService;
        this.inventoryLogMapper = inventoryLogMapper;
    }

    @Override
    @Transactional
    public void createOutbound(OutboundOrder order, List<OutboundOrderItem> items) {
        order.setStatus("SHIPPED");
        order.setCreatedAt(LocalDateTime.now());
        save(order);
        for (OutboundOrderItem item : items) {
            item.setOutboundId(order.getId());
            itemMapper.insert(item);
            inventoryService.adjustStock(item.getSkuId(), order.getWarehouseId(), item.getLocationId(), item.getBatchId(), -item.getQuantity(), 0, 0);
            InventoryLog log = new InventoryLog();
            log.setSkuId(item.getSkuId());
            log.setBatchId(item.getBatchId());
            log.setWarehouseId(order.getWarehouseId());
            log.setLocationId(item.getLocationId());
            log.setChangeType("OUTBOUND");
            log.setChangeQty(-item.getQuantity());
            log.setRefType("OUTBOUND");
            log.setRefId(order.getId());
            log.setCreatedAt(LocalDateTime.now());
            inventoryLogMapper.insert(log);
        }
    }
}
