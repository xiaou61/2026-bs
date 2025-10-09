package com.xiaou.controller;

import com.xiaou.common.BusinessException;
import com.xiaou.common.Result;
import com.xiaou.entity.Category;
import com.xiaou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/tree")
    public Result<?> getTree() {
        List<Category> categories = categoryService.getTree();
        return Result.success(categories);
    }

    @PostMapping
    public Result<?> create(@RequestBody Category category, @RequestAttribute String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }
        categoryService.save(category);
        return Result.success("创建成功");
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Category category, @RequestAttribute String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }
        category.setId(id);
        categoryService.updateById(category);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, @RequestAttribute String userRole) {
        if (!"admin".equals(userRole)) {
            throw new BusinessException(403, "无权访问");
        }
        categoryService.removeById(id);
        return Result.success("删除成功");
    }
}

