package com.groupbuy.controller;

import com.groupbuy.common.Result;
import com.groupbuy.entity.Category;
import com.groupbuy.service.CategoryService;
import com.groupbuy.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(categoryService.list());
    }

    @GetMapping("/tree")
    public Result<?> tree() {
        return Result.success(categoryService.tree());
    }

    @PostMapping("/add")
    public Result<?> add(HttpServletRequest request, @RequestBody Category category) {
        AuthUtils.requireAdmin(request);
        categoryService.add(category);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> update(HttpServletRequest request, @RequestBody Category category) {
        AuthUtils.requireAdmin(request);
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<?> delete(HttpServletRequest request, @PathVariable Long id) {
        AuthUtils.requireAdmin(request);
        categoryService.delete(id);
        return Result.success();
    }
}
