package com.xiaou.snack.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.snack.wms.entity.inventory.InventoryLog;
import com.xiaou.snack.wms.entity.inventory.StockCheck;
import com.xiaou.snack.wms.entity.inventory.StockCheckItem;
import com.xiaou.snack.wms.mapper.InventoryLogMapper;
import com.xiaou.snack.wms.mapper.StockCheckItemMapper;
import com.xiaou.snack.wms.mapper.StockCheckMapper;
import com.xiaou.snack.wms.service.InventoryService;
import com.xiaou.snack.wms.service.StockCheckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockCheckServiceImpl extends ServiceImpl<StockCheckMapper, StockCheck> implements StockCheckService {
    private final StockCheckItemMapper itemMapper;
    private final InventoryService inventoryService;
    private final InventoryLogMapper inventoryLogMapper;

    public StockCheckServiceImpl(StockCheckItemMapper itemMapper, InventoryService inventoryService, InventoryLogMapper inventoryLogMapper) {
        this.itemMapper = itemMapper;
        this.inventoryService = inventoryService;
        this.inventoryLogMapper = inventoryLogMapper;
    }

    @Override
    @Transactional
    public void createCheck(StockCheck check, List<StockCheckItem> items) {
        check.setStatus("DONE");
        check.setCreatedAt(LocalDateTime.now());
        save(check);
        for (StockCheckItem item : items) {
            item.setCheckId(check.getId());
            itemMapper.insert(item);
            int diff = item.getDiffQty() == null ? 0 : item.getDiffQty();
            if (diff != 0) {
                inventoryService.adjustStock(item.getSkuId(), check.getWarehouseId(), item.getLocationId(), item.getBatchId(), diff, 0, 0);
                InventoryLog log = new InventoryLog();
                log.setSkuId(item.getSkuId());
                log.setBatchId(item.getBatchId());
                log.setWarehouseId(check.getWarehouseId());
                log.setLocationId(item.getLocationId());
                log.setChangeType("STOCK_CHECK");
                log.setChangeQty(diff);
                log.setRefType("CHECK");
                log.setRefId(check.getId());
                log.setCreatedAt(LocalDateTime.now());
                inventoryLogMapper.insert(log);
            }
        }
    }
}
