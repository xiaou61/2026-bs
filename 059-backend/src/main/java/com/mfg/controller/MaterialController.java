package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.entity.Material;
import com.mfg.service.MaterialService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @Resource
    private MaterialService materialService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name) {
        return Result.success(materialService.page(pageNum, pageSize, name));
    }

    @PostMapping
    public Result<?> add(@RequestBody Material material) {
        materialService.add(material);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Material material) {
        materialService.update(material);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        materialService.delete(id);
        return Result.success();
    }
}
