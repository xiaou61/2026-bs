package com.disaster.controller;

import com.disaster.common.Result;
import com.disaster.service.StockService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize,
                          @RequestParam(required = false) Long warehouseId,
                          @RequestParam(required = false) Long materialId) {
        return Result.success(stockService.page(pageNum, pageSize, warehouseId, materialId));
    }

    @PostMapping("/in")
    public Result<Void> stockIn(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long warehouseId = Long.valueOf(params.get("warehouseId").toString());
        Long materialId = Long.valueOf(params.get("materialId").toString());
        Integer quantity = Integer.valueOf(params.get("quantity").toString());
        String source = (String) params.get("source");
        String remark = (String) params.get("remark");
        stockService.stockIn(warehouseId, materialId, quantity, source, userId, remark);
        return Result.success();
    }

    @PostMapping("/out")
    public Result<Void> stockOut(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long warehouseId = Long.valueOf(params.get("warehouseId").toString());
        Long materialId = Long.valueOf(params.get("materialId").toString());
        Integer quantity = Integer.valueOf(params.get("quantity").toString());
        String source = (String) params.get("source");
        String remark = (String) params.get("remark");
        stockService.stockOut(warehouseId, materialId, quantity, source, userId, remark);
        return Result.success();
    }

    @GetMapping("/record")
    public Result<?> record(@RequestParam(defaultValue = "1") int pageNum,
                            @RequestParam(defaultValue = "10") int pageSize,
                            @RequestParam(required = false) Long warehouseId,
                            @RequestParam(required = false) Long materialId,
                            @RequestParam(required = false) Integer type) {
        return Result.success(stockService.recordPage(pageNum, pageSize, warehouseId, materialId, type));
    }

    @GetMapping("/warning")
    public Result<?> warning() {
        return Result.success(stockService.warning());
    }
}
