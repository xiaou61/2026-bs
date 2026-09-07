package com.hospital.controller;

import com.github.pagehelper.PageInfo;
import com.hospital.common.Result;
import com.hospital.entity.DepartmentInfo;
import com.hospital.service.DepartmentService;
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
    public Result<PageInfo<DepartmentInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) Integer status,
                                                 @RequestAttribute("role") String role) {
        return Result.success(departmentService.page(name, status, pageNum, pageSize, role));
    }

    @GetMapping("/enabled")
    public Result<List<DepartmentInfo>> enabled() {
        return Result.success(departmentService.enabledList());
    }

    @PostMapping
    public Result<String> add(@RequestBody DepartmentInfo entity,
                              @RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role) {
        departmentService.save(entity, userId, role);
        return Result.success();
    }

    @PutMapping
    public Result<String> update(@RequestBody DepartmentInfo entity,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        departmentService.save(entity, userId, role);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        departmentService.delete(id, userId, role);
        return Result.success();
    }
}
