package com.hrm.controller;

import com.hrm.common.Result;
import com.hrm.entity.Department;
import com.hrm.service.DepartmentService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(departmentService.getById(id));
    }

    @GetMapping("/list")
    public Result getList(@RequestParam(required = false) String name,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(departmentService.getList(name, status, pageNum, pageSize));
    }

    @GetMapping("/all")
    public Result getAll() {
        return Result.success(departmentService.getAll());
    }

    @GetMapping("/tree")
    public Result getTree() {
        return Result.success(departmentService.getTree());
    }

    @PostMapping
    public Result add(@RequestBody Department department) {
        departmentService.add(department);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Department department) {
        departmentService.update(department);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        departmentService.delete(id);
        return Result.success();
    }
}
