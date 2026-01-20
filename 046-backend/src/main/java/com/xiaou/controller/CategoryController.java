package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.GarbageCategory;
import com.xiaou.mapper.GarbageCategoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "垃圾分类")
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final GarbageCategoryMapper categoryMapper;

    @Operation(summary = "分类列表")
    @GetMapping("/list")
    public Result<List<GarbageCategory>> list() {
        return Result.success(categoryMapper.selectList(
                new LambdaQueryWrapper<GarbageCategory>()
                        .eq(GarbageCategory::getStatus, 1)
                        .orderByAsc(GarbageCategory::getSortOrder)));
    }

    @Operation(summary = "分类详情")
    @GetMapping("/{id}")
    public Result<GarbageCategory> detail(@PathVariable Long id) {
        return Result.success(categoryMapper.selectById(id));
    }
}
