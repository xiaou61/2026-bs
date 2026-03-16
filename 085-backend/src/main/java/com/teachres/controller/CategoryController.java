package com.teachres.controller;

import com.github.pagehelper.PageInfo;
import com.teachres.common.Result;
import com.teachres.entity.CourseCategory;
import com.teachres.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<PageInfo<CourseCategory>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) Integer status) {
        return Result.success(categoryService.list(name, status, pageNum, pageSize));
    }

    @GetMapping("/enabled")
    public Result<List<CourseCategory>> enabled() {
        return Result.success(categoryService.enabledList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody CourseCategory category) {
        categoryService.add(category);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody CourseCategory category) {
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return Result.success();
    }
}
