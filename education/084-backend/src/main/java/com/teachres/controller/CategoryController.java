package com.teachres.controller;

import com.github.pagehelper.PageInfo;
import com.teachres.common.Result;
import com.teachres.entity.MaterialCategory;
import com.teachres.service.CategoryService;
import com.teachres.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
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
    public Result<PageInfo<MaterialCategory>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) Integer status,
                                                   HttpServletRequest request) {
        AuthUtils.requireAnyRole(request, "admin", "teacher");
        return Result.success(categoryService.list(name, status, pageNum, pageSize));
    }

    @GetMapping("/enabled")
    public Result<List<MaterialCategory>> enabled() {
        return Result.success(categoryService.enabledList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody MaterialCategory category, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        categoryService.add(category);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody MaterialCategory category, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin(request);
        categoryService.delete(id);
        return Result.success();
    }
}
