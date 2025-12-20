package com.xiaou.snack.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.snack.wms.entity.inventory.Batch;
import com.xiaou.snack.wms.entity.inventory.InventoryLog;
import com.xiaou.snack.wms.entity.order.InboundOrder;
import com.xiaou.snack.wms.entity.order.InboundOrderItem;
import com.xiaou.snack.wms.mapper.BatchMapper;
import com.xiaou.snack.wms.mapper.InboundOrderItemMapper;
import com.xiaou.snack.wms.mapper.InboundOrderMapper;
import com.xiaou.snack.wms.mapper.InventoryLogMapper;
import com.xiaou.snack.wms.service.InboundService;
import com.xiaou.snack.wms.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InboundServiceImpl extends ServiceImpl<InboundOrderMapper, InboundOrder> implements InboundService {
    private final InboundOrderItemMapper itemMapper;
    private final BatchMapper batchMapper;
    private final InventoryService inventoryService;
    private final InventoryLogMapper inventoryLogMapper;

    public InboundServiceImpl(InboundOrderItemMapper itemMapper, BatchMapper batchMapper, InventoryService inventoryService, InventoryLogMapper inventoryLogMapper) {
        this.itemMapper = itemMapper;
        this.batchMapper = batchMapper;
        this.inventoryService = inventoryService;
        this.inventoryLogMapper = inventoryLogMapper;
    }

    @Override
    @Transactional
    public void createInbound(InboundOrder order, List<InboundOrderItem> items) {
        order.setStatus("RECEIVED");
        order.setCreatedAt(LocalDateTime.now());
        save(order);
        for (InboundOrderItem item : items) {
            item.setInboundId(order.getId());
            itemMapper.insert(item);
            Batch batch = batchMapper.selectOne(lambdaQueryBatch(item));
            if (batch == null) {
                batch = new Batch();
                batch.setSkuId(item.getSkuId());
                batch.setBatchNo(item.getBatchNo());
                batch.setProductionDate(item.getProductionDate());
                batch.setExpireDate(item.getExpireDate());
                batchMapper.insert(batch);
            }
            inventoryService.adjustStock(item.getSkuId(), order.getWarehouseId(), item.getLocationId(), batch.getId(), item.getQuantity(), 0, 0);
            InventoryLog log = new InventoryLog();
            log.setSkuId(item.getSkuId());
            log.setBatchId(batch.getId());
            log.setWarehouseId(order.getWarehouseId());
            log.setLocationId(item.getLocationId());
            log.setChangeType("INBOUND");
            log.setChangeQty(item.getQuantity());
            log.setRefType("INBOUND");
            log.setRefId(order.getId());
            log.setCreatedAt(LocalDateTime.now());
            inventoryLogMapper.insert(log);
        }
    }

    private com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Batch> lambdaQueryBatch(InboundOrderItem item) {
        return new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Batch>()
                .eq(Batch::getSkuId, item.getSkuId())
                .eq(Batch::getBatchNo, item.getBatchNo());
    }
}
