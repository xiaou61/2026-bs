package com.xiaou.snack.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.snack.wms.entity.inventory.Inventory;

public interface InventoryService extends IService<Inventory> {
    void adjustStock(Long skuId, Long warehouseId, Long locationId, Long batchId, int deltaAvailable, int deltaLocked, int deltaFrozen);
}
