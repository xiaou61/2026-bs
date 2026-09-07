package com.disaster.controller;

import com.disaster.common.Result;
import com.disaster.entity.Warehouse;
import com.disaster.service.StockService;
import com.disaster.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private StockService stockService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize,
                          @RequestParam(required = false) String keyword) {
        return Result.success(warehouseService.page(pageNum, pageSize, keyword));
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(warehouseService.list());
    }

    @GetMapping("/{id}")
    public Result<Warehouse> detail(@PathVariable Long id) {
        return Result.success(warehouseService.getById(id));
    }

    @GetMapping("/{id}/stock")
    public Result<?> stock(@PathVariable Long id) {
        return Result.success(stockService.listByWarehouse(id));
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody Warehouse warehouse) {
        warehouseService.add(warehouse);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody Warehouse warehouse) {
        warehouseService.update(warehouse);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        warehouseService.delete(id);
        return Result.success();
    }
}
