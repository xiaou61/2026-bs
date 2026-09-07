package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.entity.MaterialStockRecord;
import com.mfg.service.MaterialStockRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/materialStock")
public class MaterialStockRecordController {

    @Resource
    private MaterialStockRecordService materialStockRecordService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long materialId,
                          @RequestParam(required = false) String type) {
        return Result.success(materialStockRecordService.page(pageNum, pageSize, materialId, type));
    }

    @PostMapping("/in")
    public Result<?> stockIn(@RequestBody MaterialStockRecord record) {
        materialStockRecordService.stockIn(record);
        return Result.success();
    }

    @PostMapping("/out")
    public Result<?> stockOut(@RequestBody MaterialStockRecord record) {
        materialStockRecordService.stockOut(record);
        return Result.success();
    }
}
