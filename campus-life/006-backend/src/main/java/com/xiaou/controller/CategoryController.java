package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Category;
import com.xiaou.entity.User;
import com.xiaou.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<Category>> getCategories() {
        List<Category> categories = categoryService.getActiveCategories();
        return Result.success(categories);
    }

    @GetMapping("/all")
    public Result<List<Category>> getAllCategories(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getRole())) {
            return Result.error(403, "无权限");
        }
        List<Category> categories = categoryService.getAllCategories();
        return Result.success(categories);
    }

    @PostMapping
    public Result<?> addCategory(@RequestBody Category category, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getRole())) {
            return Result.error(403, "无权限");
        }
        try {
            categoryService.addCategory(category);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<?> updateCategory(@PathVariable Long id, @RequestBody Category category, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getRole())) {
            return Result.error(403, "无权限");
        }
        try {
            category.setId(id);
            categoryService.updateCategory(category);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteCategory(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!"admin".equals(user.getRole())) {
            return Result.error(403, "无权限");
        }
        try {
            categoryService.deleteCategory(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

