package com.oa.controller;

import com.oa.common.Result;
import com.oa.entity.Department;
import com.oa.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(departmentService.getList(pageNum, pageSize));
    }

    @GetMapping("/tree")
    public Result tree() {
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
