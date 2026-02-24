package com.craft.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.craft.common.BusinessException;
import com.craft.common.Result;
import com.craft.entity.CraftCategory;
import com.craft.service.CraftCategoryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Resource
    private CraftCategoryService categoryService;

    @GetMapping("/list")
    public Result<?> list() {
        return Result.success(categoryService.list());
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        Page<CraftCategory> page = categoryService.page(pageNum, pageSize, name, status);
        return Result.success(page);
    }

    @PostMapping
    public Result<?> add(@RequestBody CraftCategory category, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        categoryService.save(category);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody CraftCategory category, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        categoryService.save(category);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        categoryService.deleteById(id);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}

