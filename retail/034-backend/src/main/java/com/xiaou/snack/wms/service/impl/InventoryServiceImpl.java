package com.xiaou.snack.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.snack.wms.common.ApiException;
import com.xiaou.snack.wms.entity.inventory.Inventory;
import com.xiaou.snack.wms.mapper.InventoryMapper;
import com.xiaou.snack.wms.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {
    @Override
    @Transactional
    public void adjustStock(Long skuId, Long warehouseId, Long locationId, Long batchId, int deltaAvailable, int deltaLocked, int deltaFrozen) {
        Inventory inventory = lambdaQuery()
                .eq(Inventory::getSkuId, skuId)
                .eq(Inventory::getWarehouseId, warehouseId)
                .eq(Inventory::getLocationId, locationId)
                .eq(Inventory::getBatchId, batchId)
                .one();
        if (inventory == null) {
            inventory = new Inventory();
            inventory.setSkuId(skuId);
            inventory.setWarehouseId(warehouseId);
            inventory.setLocationId(locationId);
            inventory.setBatchId(batchId);
            inventory.setQuantityAvailable(0);
            inventory.setQuantityLocked(0);
            inventory.setQuantityFrozen(0);
            save(inventory);
        }
        int newAvailable = inventory.getQuantityAvailable() + deltaAvailable;
        int newLocked = inventory.getQuantityLocked() + deltaLocked;
        int newFrozen = inventory.getQuantityFrozen() + deltaFrozen;
        if (newAvailable < 0 || newLocked < 0 || newFrozen < 0) {
            throw new ApiException(409, "insufficient stock");
        }
        inventory.setQuantityAvailable(newAvailable);
        inventory.setQuantityLocked(newLocked);
        inventory.setQuantityFrozen(newFrozen);
        updateById(inventory);
    }
}
