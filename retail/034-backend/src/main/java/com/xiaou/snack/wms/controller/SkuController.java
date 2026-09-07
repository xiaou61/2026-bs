package com.xiaou.snack.wms.controller;

import com.xiaou.snack.wms.common.Result;
import com.xiaou.snack.wms.entity.basic.Sku;
import com.xiaou.snack.wms.service.SkuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sku")
public class SkuController {
    private final SkuService skuService;

    public SkuController(SkuService skuService) {
        this.skuService = skuService;
    }

    @PostMapping
    public Result<Sku> create(@RequestBody Sku sku) {
        skuService.save(sku);
        return Result.success(sku);
    }

    @PutMapping("/{id}")
    public Result<Sku> update(@PathVariable Long id, @RequestBody Sku sku) {
        sku.setId(id);
        skuService.updateById(sku);
        return Result.success(sku);
    }

    @GetMapping
    public Result<List<Sku>> list() {
        return Result.success(skuService.list());
    }
}
