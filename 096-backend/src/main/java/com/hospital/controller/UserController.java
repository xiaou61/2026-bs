package com.hospital.controller;

import com.github.pagehelper.PageInfo;
import com.hospital.common.Result;
import com.hospital.entity.SysUser;
import com.hospital.service.UserService;
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

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/page")
    public Result<PageInfo<SysUser>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestParam(required = false) String role,
                                          @RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) Integer status,
                                          @RequestAttribute("role") String currentRole) {
        return Result.success(userService.page(role, keyword, status, pageNum, pageSize, currentRole));
    }

    @PostMapping
    public Result<String> add(@RequestBody SysUser entity, @RequestAttribute("role") String currentRole) {
        userService.save(entity, currentRole);
        return Result.success();
    }

    @PutMapping
    public Result<String> update(@RequestBody SysUser entity, @RequestAttribute("role") String currentRole) {
        userService.save(entity, currentRole);
        return Result.success();
    }

    @PutMapping("/status")
    public Result<String> updateStatus(@RequestBody Map<String, Object> params, @RequestAttribute("role") String currentRole) {
        Long id = params.get("id") == null ? null : Long.valueOf(String.valueOf(params.get("id")));
        Integer status = params.get("status") == null ? null : Integer.valueOf(String.valueOf(params.get("status")));
        userService.updateStatus(id, status, currentRole);
        return Result.success();
    }

    @PutMapping("/profile")
    public Result<String> updateProfile(@RequestBody SysUser entity, @RequestAttribute("userId") Long userId) {
        userService.updateProfile(entity, userId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id, @RequestAttribute("role") String currentRole) {
        userService.delete(id, currentRole);
        return Result.success();
    }
}
