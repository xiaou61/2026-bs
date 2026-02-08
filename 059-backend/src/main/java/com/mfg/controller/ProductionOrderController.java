package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.entity.ProductionOrder;
import com.mfg.service.ProductionOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/productionOrder")
public class ProductionOrderController {

    @Resource
    private ProductionOrderService productionOrderService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String orderNo,
                          @RequestParam(required = false) String status) {
        return Result.success(productionOrderService.page(pageNum, pageSize, orderNo, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody ProductionOrder order) {
        productionOrderService.add(order);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody ProductionOrder order) {
        productionOrderService.update(order);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        productionOrderService.delete(id);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<?> updateStatus(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        String status = params.get("status").toString();
        productionOrderService.updateStatus(id, status);
        return Result.success();
    }
}
