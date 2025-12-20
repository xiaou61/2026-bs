package com.xiaou.snack.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.snack.wms.common.Result;
import com.xiaou.snack.wms.entity.inventory.Inventory;
import com.xiaou.snack.wms.entity.inventory.InventoryLog;
import com.xiaou.snack.wms.mapper.InventoryLogMapper;
import com.xiaou.snack.wms.service.InventoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    private final InventoryLogMapper inventoryLogMapper;

    public InventoryController(InventoryService inventoryService, InventoryLogMapper inventoryLogMapper) {
        this.inventoryService = inventoryService;
        this.inventoryLogMapper = inventoryLogMapper;
    }

    @GetMapping
    public Result<List<Inventory>> list(@RequestParam(required = false) Long skuId) {
        LambdaQueryWrapper<Inventory> wrapper = new LambdaQueryWrapper<>();
        if (skuId != null) {
            wrapper.eq(Inventory::getSkuId, skuId);
        }
        return Result.success(inventoryService.list(wrapper));
    }

    @GetMapping("/logs")
    public Result<List<InventoryLog>> logs(@RequestParam(required = false) Long skuId) {
        LambdaQueryWrapper<InventoryLog> wrapper = new LambdaQueryWrapper<>();
        if (skuId != null) {
            wrapper.eq(InventoryLog::getSkuId, skuId);
        }
        return Result.success(inventoryLogMapper.selectList(wrapper));
    }
}
