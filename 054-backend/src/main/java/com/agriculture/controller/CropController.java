package com.agriculture.controller;

import com.agriculture.common.Result;
import com.agriculture.entity.Crop;
import com.agriculture.entity.CropCategory;
import com.agriculture.service.CropCategoryService;
import com.agriculture.service.CropService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/crop")
public class CropController {

    @Autowired
    private CropService cropService;

    @Autowired
    private CropCategoryService categoryService;

    @GetMapping("/page")
    public Result<Page<Crop>> getPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) Long categoryId) {
        return Result.success(cropService.getCropPage(pageNum, pageSize, name, categoryId));
    }

    @GetMapping("/{id}")
    public Result<Crop> getById(@PathVariable Long id) {
        return Result.success(cropService.getById(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody Crop crop) {
        cropService.save(crop);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Crop crop) {
        cropService.updateById(crop);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        cropService.removeById(id);
        return Result.success();
    }

    @GetMapping("/category/list")
    public Result<List<CropCategory>> getCategoryList() {
        return Result.success(categoryService.getList());
    }

    @GetMapping("/category/tree")
    public Result<List<CropCategory>> getCategoryTree() {
        return Result.success(categoryService.getTree());
    }

    @PostMapping("/category")
    public Result<?> addCategory(@RequestBody CropCategory category) {
        categoryService.save(category);
        return Result.success();
    }

    @PutMapping("/category")
    public Result<?> updateCategory(@RequestBody CropCategory category) {
        categoryService.updateById(category);
        return Result.success();
    }

    @DeleteMapping("/category/{id}")
    public Result<?> deleteCategory(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.success();
    }
}
