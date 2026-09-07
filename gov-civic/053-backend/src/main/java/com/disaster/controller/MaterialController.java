package com.disaster.controller;

import com.disaster.common.Result;
import com.disaster.entity.Material;
import com.disaster.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize,
                          @RequestParam(required = false) Long categoryId,
                          @RequestParam(required = false) String keyword) {
        return Result.success(materialService.page(pageNum, pageSize, categoryId, keyword));
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(materialService.list());
    }

    @GetMapping("/{id}")
    public Result<Material> detail(@PathVariable Long id) {
        return Result.success(materialService.getById(id));
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody Material material) {
        materialService.add(material);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody Material material) {
        materialService.update(material);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        materialService.delete(id);
        return Result.success();
    }
}
