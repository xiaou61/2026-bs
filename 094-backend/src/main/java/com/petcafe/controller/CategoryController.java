package com.petcafe.controller;

import com.petcafe.common.Result;
import com.petcafe.entity.MenuCategory;
import com.petcafe.service.CategoryService;
import com.petcafe.utils.AuthUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String name,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        return Result.success(categoryService.page(pageNum, pageSize, name, status));
    }

    @GetMapping("/all")
    public Result<?> all() {
        return Result.success(categoryService.listAll());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody MenuCategory category, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        categoryService.save(category);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireManager((String) request.getAttribute("role"));
        categoryService.deleteById(id);
        return Result.success();
    }
}
