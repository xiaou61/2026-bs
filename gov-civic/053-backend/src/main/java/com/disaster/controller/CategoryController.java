package com.disaster.controller;

import com.disaster.common.Result;
import com.disaster.entity.MaterialCategory;
import com.disaster.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(categoryService.list());
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(categoryService.listAll());
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody MaterialCategory category) {
        categoryService.add(category);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<Void> update(@RequestBody MaterialCategory category) {
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return Result.success();
    }
}
