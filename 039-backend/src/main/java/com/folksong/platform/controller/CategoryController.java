package com.folksong.platform.controller;

import com.folksong.platform.common.Result;
import com.folksong.platform.entity.Category;
import com.folksong.platform.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "分类管理")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "获取所有启用的分类")
    @GetMapping
    public Result<List<Category>> getAllActiveCategories() {
        return Result.success(categoryService.getAllActiveCategories());
    }

    @Operation(summary = "根据ID获取分类")
    @GetMapping("/{id}")
    public Result<Category> getCategoryById(@PathVariable Long id) {
        return Result.success(categoryService.getCategoryById(id));
    }

    @Operation(summary = "根据地区获取分类")
    @GetMapping("/region/{region}")
    public Result<List<Category>> getCategoriesByRegion(@PathVariable String region) {
        return Result.success(categoryService.getCategoriesByRegion(region));
    }
}
