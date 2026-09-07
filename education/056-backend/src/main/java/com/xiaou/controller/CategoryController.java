package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Category;
import com.xiaou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          String keyword) {
        return Result.success(categoryService.getList(pageNum, pageSize, keyword));
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return Result.success();
    }
}
