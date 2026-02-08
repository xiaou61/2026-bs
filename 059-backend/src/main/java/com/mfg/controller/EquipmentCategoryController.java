package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.entity.EquipmentCategory;
import com.mfg.service.EquipmentCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/equipmentCategory")
public class EquipmentCategoryController {

    @Resource
    private EquipmentCategoryService equipmentCategoryService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name) {
        return Result.success(equipmentCategoryService.page(pageNum, pageSize, name));
    }

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(equipmentCategoryService.list());
    }

    @PostMapping
    public Result<?> add(@RequestBody EquipmentCategory category) {
        equipmentCategoryService.add(category);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody EquipmentCategory category) {
        equipmentCategoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        equipmentCategoryService.delete(id);
        return Result.success();
    }
}
