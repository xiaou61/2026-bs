package com.xiaou.snack.wms.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.snack.wms.entity.inventory.Batch;
import com.xiaou.snack.wms.entity.inventory.Inventory;
import com.xiaou.snack.wms.entity.order.NotificationRecord;
import com.xiaou.snack.wms.mapper.BatchMapper;
import com.xiaou.snack.wms.mapper.InventoryMapper;
import com.xiaou.snack.wms.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PrewarnTask {
    private final BatchMapper batchMapper;
    private final InventoryMapper inventoryMapper;
    private final NotificationService notificationService;

    public PrewarnTask(BatchMapper batchMapper, InventoryMapper inventoryMapper, NotificationService notificationService) {
        this.batchMapper = batchMapper;
        this.inventoryMapper = inventoryMapper;
        this.notificationService = notificationService;
    }

    @Scheduled(fixedDelay = 3600000)
    public void nearExpireWarn() {
        LocalDate threshold = LocalDate.now().plusDays(7);
        List<Batch> batches = batchMapper.selectList(new LambdaQueryWrapper<Batch>().le(Batch::getExpireDate, threshold));
        for (Batch batch : batches) {
            NotificationRecord record = new NotificationRecord();
            record.setType("EXPIRE");
            record.setTarget("warehouse");
            record.setContent("batch " + batch.getBatchNo() + " approaching expiry");
            record.setStatus("NEW");
            record.setCreatedAt(LocalDateTime.now());
            notificationService.notifyRecord(record);
        }
    }

    @Scheduled(fixedDelay = 3600000)
    public void lowStockWarn() {
        List<Inventory> inventories = inventoryMapper.selectList(null);
        for (Inventory inv : inventories) {
            if (inv.getQuantityAvailable() != null && inv.getQuantityAvailable() < 10) {
                NotificationRecord record = new NotificationRecord();
                record.setType("LOW_STOCK");
                record.setTarget("warehouse");
                record.setContent("sku " + inv.getSkuId() + " low stock");
                record.setStatus("NEW");
                record.setCreatedAt(LocalDateTime.now());
                notificationService.notifyRecord(record);
            }
        }
    }
}
