package com.repair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.repair.common.Result;
import com.repair.entity.ApplianceCategory;
import com.repair.service.ApplianceCategoryService;
import com.repair.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class ApplianceCategoryController {

    @Autowired
    private ApplianceCategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result<Page<ApplianceCategory>> getList(@RequestParam(defaultValue = "1") int pageNum,
                                                   @RequestParam(defaultValue = "10") int pageSize,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) Integer status,
                                                   @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        return Result.success(categoryService.getList(pageNum, pageSize, name, status));
    }

    @GetMapping("/public/list")
    public Result<List<ApplianceCategory>> getEnabledList() {
        return Result.success(categoryService.getEnabledList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody ApplianceCategory category, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        categoryService.add(category);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody ApplianceCategory category, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("userId") String userId) {
        userService.requireAdmin(Long.parseLong(userId));
        categoryService.delete(id);
        return Result.success();
    }
}
