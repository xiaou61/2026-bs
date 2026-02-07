package com.milk.controller;

import com.milk.common.Result;
import com.milk.entity.MilkCategory;
import com.milk.service.MilkCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Resource
    private MilkCategoryService milkCategoryService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(milkCategoryService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name) {
        return Result.success(milkCategoryService.page(pageNum, pageSize, name));
    }

    @PostMapping
    public Result<?> add(@RequestBody MilkCategory category) {
        milkCategoryService.save(category);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody MilkCategory category) {
        milkCategoryService.save(category);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        milkCategoryService.deleteById(id);
        return Result.success();
    }
}
