package com.xiaou.snack.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.snack.wms.entity.inventory.InventoryLog;
import com.xiaou.snack.wms.entity.order.TransferOrder;
import com.xiaou.snack.wms.entity.order.TransferOrderItem;
import com.xiaou.snack.wms.mapper.InventoryLogMapper;
import com.xiaou.snack.wms.mapper.TransferOrderItemMapper;
import com.xiaou.snack.wms.mapper.TransferOrderMapper;
import com.xiaou.snack.wms.service.InventoryService;
import com.xiaou.snack.wms.service.TransferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransferServiceImpl extends ServiceImpl<TransferOrderMapper, TransferOrder> implements TransferService {
    private final TransferOrderItemMapper itemMapper;
    private final InventoryService inventoryService;
    private final InventoryLogMapper inventoryLogMapper;

    public TransferServiceImpl(TransferOrderItemMapper itemMapper, InventoryService inventoryService, InventoryLogMapper inventoryLogMapper) {
        this.itemMapper = itemMapper;
        this.inventoryService = inventoryService;
        this.inventoryLogMapper = inventoryLogMapper;
    }

    @Override
    @Transactional
    public void createTransfer(TransferOrder order, List<TransferOrderItem> items) {
        order.setStatus("DONE");
        order.setCreatedAt(LocalDateTime.now());
        save(order);
        for (TransferOrderItem item : items) {
            item.setTransferId(order.getId());
            itemMapper.insert(item);
            inventoryService.adjustStock(item.getSkuId(), order.getFromWarehouseId(), item.getFromLocationId(), item.getBatchId(), -item.getQuantity(), 0, 0);
            inventoryService.adjustStock(item.getSkuId(), order.getToWarehouseId(), item.getToLocationId(), item.getBatchId(), item.getQuantity(), 0, 0);
            InventoryLog logOut = new InventoryLog();
            logOut.setSkuId(item.getSkuId());
            logOut.setBatchId(item.getBatchId());
            logOut.setWarehouseId(order.getFromWarehouseId());
            logOut.setLocationId(item.getFromLocationId());
            logOut.setChangeType("TRANSFER_OUT");
            logOut.setChangeQty(-item.getQuantity());
            logOut.setRefType("TRANSFER");
            logOut.setRefId(order.getId());
            logOut.setCreatedAt(LocalDateTime.now());
            inventoryLogMapper.insert(logOut);
            InventoryLog logIn = new InventoryLog();
            logIn.setSkuId(item.getSkuId());
            logIn.setBatchId(item.getBatchId());
            logIn.setWarehouseId(order.getToWarehouseId());
            logIn.setLocationId(item.getToLocationId());
            logIn.setChangeType("TRANSFER_IN");
            logIn.setChangeQty(item.getQuantity());
            logIn.setRefType("TRANSFER");
            logIn.setRefId(order.getId());
            logIn.setCreatedAt(LocalDateTime.now());
            inventoryLogMapper.insert(logIn);
        }
    }
}
