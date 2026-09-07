package com.enrollment.controller;

import com.enrollment.common.Result;
import com.enrollment.entity.Major;
import com.enrollment.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/major")
public class MajorController {

    @Autowired
    private MajorService majorService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) Long departmentId) {
        return Result.success(majorService.getPage(pageNum, pageSize, name, departmentId));
    }

    @GetMapping("/list/{departmentId}")
    public Result<?> getListByDepartment(@PathVariable Long departmentId) {
        return Result.success(majorService.getListByDepartment(departmentId));
    }

    @GetMapping("/list")
    public Result<?> getAll() {
        return Result.success(majorService.getAll());
    }

    @PostMapping
    public Result<?> add(@RequestBody Major major) {
        majorService.add(major);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Major major) {
        majorService.update(major);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        majorService.delete(id);
        return Result.success();
    }
}
