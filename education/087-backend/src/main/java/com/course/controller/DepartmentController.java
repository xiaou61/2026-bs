package com.course.controller;

import com.github.pagehelper.PageInfo;
import com.course.common.Result;
import com.course.entity.DepartmentInfo;
import com.course.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/list")
    public Result<PageInfo<DepartmentInfo>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) Integer status) {
        return Result.success(departmentService.list(name, status, pageNum, pageSize));
    }

    @GetMapping("/enabled")
    public Result<List<DepartmentInfo>> enabled() {
        return Result.success(departmentService.enabledList());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody DepartmentInfo entity, @RequestAttribute("role") String role) {
        departmentService.add(entity, role);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody DepartmentInfo entity, @RequestAttribute("role") String role) {
        departmentService.update(entity, role);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("role") String role) {
        departmentService.delete(id, role);
        return Result.success();
    }
}
