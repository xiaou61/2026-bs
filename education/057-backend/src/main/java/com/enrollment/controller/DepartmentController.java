package com.enrollment.controller;

import com.enrollment.common.Result;
import com.enrollment.entity.Department;
import com.enrollment.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String name) {
        return Result.success(departmentService.getPage(pageNum, pageSize, name));
    }

    @GetMapping("/list")
    public Result<?> getList() {
        return Result.success(departmentService.getList());
    }

    @PostMapping
    public Result<?> add(@RequestBody Department department) {
        departmentService.add(department);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Department department) {
        departmentService.update(department);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        departmentService.delete(id);
        return Result.success();
    }
}
