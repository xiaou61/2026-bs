package com.enrollment.controller;

import com.enrollment.common.Result;
import com.enrollment.entity.Admin;
import com.enrollment.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String username,
                             @RequestParam(required = false) String name) {
        return Result.success(adminService.getPage(pageNum, pageSize, username, name));
    }

    @PostMapping
    public Result<?> add(@RequestBody Admin admin) {
        adminService.add(admin);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Admin admin) {
        adminService.update(admin);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        adminService.delete(id);
        return Result.success();
    }
}
